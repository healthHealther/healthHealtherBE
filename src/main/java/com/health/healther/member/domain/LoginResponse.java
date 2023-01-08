package com.health.healther.member.domain;

import com.health.healther.member.domain.type.LoginType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private Long id;
	private String name;
	private String nickName;
	private String phone;
	private LoginType loginType;
	private String tokenType;
	private String accessToken;
	private String refreshToken;
}
