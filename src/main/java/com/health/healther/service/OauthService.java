package com.health.healther.service;

import static com.health.healther.constant.MemberStatus.*;
import static com.health.healther.domain.model.Member.*;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.health.healther.config.JwtAuthenticationProvider;
import com.health.healther.constant.LoginType;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.dto.member.LoginResponseDto;
import com.health.healther.dto.member.SignUpForm;
import com.health.healther.dto.member.userInfo.KakaoUserInfo;
import com.health.healther.dto.member.userInfo.OAuth2UserInfo;
import com.health.healther.exception.member.InvalidAccessException;

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
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	@Transactional
	public LoginResponseDto createJwtAuth(String providerName, String code) {
		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
		OAuth2AccessTokenResponse tokenResponse = getToken(provider, code);
		Member member = saveMemberWithKaKaoUserInfo(providerName, tokenResponse, provider);
		if (member.getMemberStatus().equals(NEED_DATA)) {
			return LoginResponseDto.builder()
				.oauthId(member.getOauthId())
				.nickName(member.getNickName())
				.loginType(member.getLoginType())
				.memberStatus(member.getMemberStatus())
				.build();
		}
		String accessToken = jwtAuthenticationProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();
		return LoginResponseDto.builder()
			.nickName(member.getNickName())
			.loginType(member.getLoginType())
			.memberStatus(member.getMemberStatus())
			.tokenType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Transactional
	public LoginResponseDto signUpAndCreateJwtAuth(String oauthId, SignUpForm form) {
		Member member = memberRepository.findByOauthId(oauthId)
			.orElseThrow(() -> new InvalidAccessException("잘못된 접근입니다."));
		member.signUp(form.getName(), form.getNickName(), form.getPhone());
		String accessToken = jwtAuthenticationProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();
		return LoginResponseDto.builder()
			.name(member.getName())
			.nickName(member.getNickName())
			.phone(member.getPhone())
			.loginType(member.getLoginType())
			.memberStatus(member.getMemberStatus())
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

	private OAuth2AccessTokenResponse getToken(ClientRegistration provider, String code) {
		return WebClient.create()
			.post()
			.uri(provider.getProviderDetails().getTokenUri())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.bodyValue(tokenRequest(provider, code))
			.retrieve()
			.bodyToMono(OAuth2AccessTokenResponse.class)
			.block();
	}

	private Map<String, Object> getUserAttribute(ClientRegistration provider, OAuth2AccessTokenResponse tokenResponse) {
		return WebClient.create()
			.get()
			.uri(provider.getProviderDetails().getUserInfoEndpoint().getUri())
			.headers(header -> header.setBearerAuth(tokenResponse.getAccessToken().toString()))
			.retrieve()
			.bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
			})
			.block();
	}

	private Member saveMemberWithKaKaoUserInfo(String providerName, OAuth2AccessTokenResponse tokenResponse,
		ClientRegistration provider) {
		Map<String, Object> attributes = getUserAttribute(provider, tokenResponse);
		OAuth2UserInfo oAuth2UserInfo;
		if (providerName.equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(attributes);
		} else {
			log.info("잘못된 접근입니다.");
			throw new InvalidAccessException("잘못된 접근입니다.");
		}
		String oauthProvider = oAuth2UserInfo.getProvider();
		String oauthProviderId = oAuth2UserInfo.getProviderId();
		String oauthNickName = oAuth2UserInfo.getNickName();
		Optional<Member> optionalMember = memberRepository.findByOauthId(oauthProviderId);
		Member member;
		if (optionalMember.isEmpty()) {
			member = getByKakaoUserInfo(oauthNickName, NEED_DATA, oauthProviderId, getloginType(oauthProvider));
			memberRepository.save(member);
			return member;
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

