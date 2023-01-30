package com.health.healther.service;

import static com.health.healther.constant.MemberStatus.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.health.healther.config.JwtTokenProvider;
import com.health.healther.constant.LoginType;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.dto.member.MemberLoginResponseDto;
import com.health.healther.dto.member.MemberOauthResponseDto;
import com.health.healther.dto.member.MemberSignUpRequestDto;
import com.health.healther.dto.member.OauthTokenResponseDto;
import com.health.healther.dto.member.Token;
import com.health.healther.dto.member.userInfo.GoogleUserInfo;
import com.health.healther.dto.member.userInfo.KakaoUserInfo;
import com.health.healther.dto.member.userInfo.OAuth2UserInfo;
import com.health.healther.exception.member.InvalidAccessException;
import com.health.healther.exception.member.NotFoundMemberException;
import com.health.healther.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OauthService {
	private static final String BEARER_TYPE = "Bearer";

	private final InMemoryClientRegistrationRepository inMemoryRepository;
	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtAuthenticationProvider;
	private final RedisUtil redisUtil;

	@Transactional
	public Member getOauth(String providerName, String code) {
		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName.toLowerCase());
		OauthTokenResponseDto tokenResponse = getToken(provider, code);
		return saveMemberWithUserInfo(providerName.toLowerCase(), tokenResponse, provider);
	}

	public MemberOauthResponseDto needDataResult(Member member) {
		return MemberOauthResponseDto.builder()
			.name(member.getName())
			.nickName(member.getNickName())
			.oauthId(member.getOauthId())
			.build();
	}

	public MemberLoginResponseDto loginResult(Member member) {
		return getMemberLoginResponseDto(member);
	}

	@Transactional
	public MemberLoginResponseDto signUpAndCreateJwtAuth(MemberSignUpRequestDto memberSignUpRequestDto) {
		Member member = memberRepository.findByOauthId(memberSignUpRequestDto.getOauthId())
			.orElseThrow(() -> new NotFoundMemberException("회원 정보를 찾을 수 없습니다."));
		member.updateFromSignUpForm(
			memberSignUpRequestDto.getName(),
			memberSignUpRequestDto.getNickName(),
			memberSignUpRequestDto.getPhone()
		);
		return getMemberLoginResponseDto(member);
	}

	private MemberLoginResponseDto getMemberLoginResponseDto(Member member) {
		Token accessToken = jwtAuthenticationProvider.createAccessToken(String.valueOf(member.getId()));
		Token refreshToken = jwtAuthenticationProvider.createRefreshToken();
		ZoneId zid = ZoneId.of("Asia/Seoul");
		ZonedDateTime expiredTime = (LocalDateTime.now().atZone(zid)).plusSeconds(accessToken.getExpiredTime() / 1000);
		redisUtil.setDataExpire(String.valueOf(member.getId()), refreshToken.getValue(), refreshToken.getExpiredTime());
		return MemberLoginResponseDto.builder()
			.tokenType(BEARER_TYPE)
			.accessToken(accessToken.getValue())
			.expiredTime(expiredTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
			.refreshToken(refreshToken.getValue())
			.build();
	}

	private MultiValueMap<String, String> tokenRequest(ClientRegistration provider, String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", "authorization_code");
		formData.add("redirect_uri", provider.getRedirectUri());
		formData.add("client_secret", provider.getClientSecret());
		formData.add("client_id", provider.getClientId());
		return formData;
	}

	private OauthTokenResponseDto getToken(ClientRegistration provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getProviderDetails().getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(provider, code))
			.retrieve()
			.bodyToMono(OauthTokenResponseDto.class)
			.block();
	}

	private Map<String, Object> getUserAttribute(ClientRegistration provider, OauthTokenResponseDto tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private Member saveMemberWithUserInfo(String providerName, OauthTokenResponseDto tokenResponse,
		ClientRegistration provider) {
		Map<String, Object> attributes = getUserAttribute(provider, tokenResponse);
		OAuth2UserInfo oAuth2UserInfo;
		String oauthNickName = null;
		String oauthName = null;
		if (providerName.equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(attributes);
			oauthNickName = ((KakaoUserInfo)oAuth2UserInfo).getNickName();
		} else if (providerName.equals("google")) {
			oAuth2UserInfo = new GoogleUserInfo(attributes);
			oauthName = ((GoogleUserInfo)oAuth2UserInfo).getName();
		} else {
			log.info("잘못된 접근입니다.");
			throw new InvalidAccessException("잘못된 접근입니다.");
		}
		String oauthProvider = oAuth2UserInfo.getProvider();
		String oauthProviderId = oAuth2UserInfo.getProviderId();
		Optional<Member> optionalMember = memberRepository.findByOauthId(oauthProviderId);
		if (optionalMember.isEmpty()) {
			Member member = Member.builder()
				.name(oauthName)
				.nickName(oauthNickName)
				.memberStatus(NEED_DATA)
				.loginType(getloginType(oauthProvider))
				.oauthId(oauthProviderId)
				.build();
			return memberRepository.save(member);
		} else {
			return optionalMember.get();
		}
	}

	private LoginType getloginType(String provide) {
		if (Objects.equals(provide, "kakao")) {
			return LoginType.KAKAO;
		} else {
			return LoginType.GOOGLE;
		}
	}
}

