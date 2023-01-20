package com.health.healther.dto.member.userInfo;

import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo {
	private final Map<String, Object> attributes;

	public GoogleUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return String.valueOf(attributes.get("sub"));
	}

	@Override
	public String getProvider() {
		return "google";
	}

	public String getName() {
		return String.valueOf(attributes.get("name"));
	}
}
