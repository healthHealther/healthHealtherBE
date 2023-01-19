package com.health.healther.service;

import static com.health.healther.constant.MemberStatus.*;
import static com.health.healther.exception.member.MemberErrorCode.*;

import java.nio.charset.StandardCharsets;
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
import com.health.healther.dto.member.LoginResponse;
import com.health.healther.dto.member.OauthTokenResponse;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.dto.member.userInfo.GoogleUserInfo;
import com.health.healther.dto.member.userInfo.KakaoUserInfo;
import com.health.healther.dto.member.userInfo.OAuth2UserInfo;
import com.health.healther.exception.member.MemberCustomException;

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

	@Transactional
	public LoginResponse getOauth(String providerName, String code) {
		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
		OauthTokenResponse tokenResponse = getToken(provider, code);
		Member member = saveMemberWithUserInfo(providerName, tokenResponse, provider);
		if (member.getMemberStatus().equals(NEED_DATA)) {
			return LoginResponse.builder()
				.oauthId(member.getOauthId())
				.nickName(member.getNickName())
				.build();
		} else {
			String accessToken = jwtAuthenticationProvider.createAccessToken(String.valueOf(member.getId()));
			String refreshToken = jwtAuthenticationProvider.createRefreshToken();
			return LoginResponse.builder()
				.tokenType(BEARER_TYPE)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
		}
	}

	@Transactional
	public LoginResponse signUpAndCreateJwtAuth(String oauthId, SignUpForm form) {
		Member member = memberRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new MemberCustomException(NOT_FOUND_MEMBER));
		member.updateFromSignUpForm(form.getName(), form.getNickName(), form.getPhone());
		String accessToken = jwtAuthenticationProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();
		return LoginResponse.builder()
			.nickName(member.getNickName())
			.tokenType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
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

	private OauthTokenResponse getToken(ClientRegistration provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getProviderDetails().getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(provider, code))
			.retrieve()
			.bodyToMono(OauthTokenResponse.class)
			.block();
	}

	private Map<String, Object> getUserAttribute(ClientRegistration provider, OauthTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private Member saveMemberWithUserInfo(String providerName, OauthTokenResponse tokenResponse,
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
			throw new MemberCustomException(INVALID_ACCESS);
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

