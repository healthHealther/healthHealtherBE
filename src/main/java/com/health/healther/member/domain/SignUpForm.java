package com.health.healther.member.domain;

import com.health.healther.member.domain.model.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpForm {
	private String oauthId;
	private String name;
	private String nickName;
	private String phone;

	public static SignUpForm from(Member member) {
		return SignUpForm.builder()
			.oauthId(member.getOauthId())
			.nickName(member.getNickName())
			.build();
	}
}
