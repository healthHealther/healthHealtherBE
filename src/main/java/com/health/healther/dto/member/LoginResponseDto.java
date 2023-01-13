package com.health.healther.dto.member;

import com.health.healther.constant.LoginType;
import com.health.healther.constant.MemberStatus;
import com.health.healther.domain.model.Member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {
	private String name;
	private String nickName;
	private String phone;
	private String oauthId;
	private MemberStatus memberStatus;
	private LoginType loginType;
	private String tokenType;
	private String accessToken;
	private String refreshToken;

	@Builder
	public LoginResponseDto(String name, String nickName, String phone, String oauthId, MemberStatus memberStatus,
		LoginType loginType, String tokenType, String accessToken, String refreshToken) {
		this.name = name;
		this.nickName = nickName;
		this.phone = phone;
		this.oauthId = oauthId;
		this.memberStatus = memberStatus;
		this.loginType = loginType;
		this.tokenType = tokenType;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
