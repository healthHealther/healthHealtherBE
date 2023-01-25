package com.health.healther.dto.member.userInfo;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {
	private final Map<String, Object> attributes;

	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	public String getNickName() {
		return (String)getProfile().get("nickname");
	}

	private Map<String, Object> getKakaoAccount() {
		return (Map<String, Object>)attributes.get("kakao_account");
	}

	private Map<String, Object> getProfile() {
		return (Map<String, Object>)getKakaoAccount().get("profile");
	}
}
