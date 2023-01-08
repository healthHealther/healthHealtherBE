package com.health.healther.member.service;

import static com.health.healther.member.domain.type.MemberStatus.*;

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

import com.health.healther.member.config.JwtAuthenticationProvider;
import com.health.healther.member.domain.LoginResponse;
import com.health.healther.member.domain.SignUpForm;
import com.health.healther.member.domain.model.Member;
import com.health.healther.member.domain.repository.MemberRepository;
import com.health.healther.member.domain.type.LoginType;
import com.health.healther.member.domain.userInfo.KakaoUserInfo;
import com.health.healther.member.domain.userInfo.OAuth2UserInfo;
import com.health.healther.member.exception.CustomException;
import com.health.healther.member.exception.ErrorCode;

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
	public LoginResponse createJwtAuth(String providerName, String code) {
		ClientRegistration provider = inMemoryRepository.findByRegistrationId(providerName);
		OAuth2AccessTokenResponse tokenResponse = getToken(provider, code);
		Member member = getUserNickname(providerName, tokenResponse, provider);
		if (member.getMemberStatus().equals(NEED_DATA)) {
			//TODO: 회원가입창으로 redirect??
			return null;
		}
		String accessToken = jwtAuthenticationProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtAuthenticationProvider.createRefreshToken();
		return LoginResponse.builder()
			.id(member.getId())
			.loginType(member.getLoginType())
			.tokenType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	@Transactional
	public Member signUp(SignUpForm form) {
		Member member = memberRepository.findByOauthId(form.getOauthId())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_ACCESS));
		member.signUp(form.getName(), form.getNickName(), form.getPhone());
		return member;
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

	private Member getUserNickname(String providerName, OAuth2AccessTokenResponse tokenResponse,
		ClientRegistration provider) {
		Map<String, Object> attributes = getUserAttribute(provider, tokenResponse);
		OAuth2UserInfo oAuth2UserInfo;
		if (providerName.equals("kakao")) {
			oAuth2UserInfo = new KakaoUserInfo(attributes);
		} else {
			log.info("잘못된 접근입니다.");
			throw new CustomException(ErrorCode.INVALID_ACCESS);
		}
		String oauthProvider = oAuth2UserInfo.getProvider();
		String oauthProviderId = oAuth2UserInfo.getProviderId();
		String oauthNickName = oAuth2UserInfo.getNickName();
		Optional<Member> optionalMember = memberRepository.findByOauthId(oauthProviderId);
		Member member;
		if (optionalMember.isEmpty()) {
			member = Member.builder()
				.nickName(oauthNickName)
				.memberStatus(NEED_DATA)
				.loginType(getLoginType(oauthProvider))
				.oauthId(oauthProviderId)
				.build();
			memberRepository.save(member);
			return member;
		} else {
			return optionalMember.get();
		}
	}

	private LoginType getLoginType(String provide) {
		if (Objects.equals(provide, "kakao")) {
			return LoginType.KAKAO;
		} else {
			return LoginType.GOOGLE;
		}
	}
}
