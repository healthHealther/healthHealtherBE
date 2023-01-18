package com.health.healther.dto.member;

import com.health.healther.constant.LoginType;
import com.health.healther.constant.MemberStatus;
import com.health.healther.domain.model.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberDto {
	private Long id;

	private String name;

	private String nickName;

	private String phone;

	private MemberStatus memberStatus;

	private String oauthId;

	private LoginType loginType;

	public static MemberDto from(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.name(member.getName())
			.nickName(member.getNickName())
			.phone(member.getPhone())
			.memberStatus(member.getMemberStatus())
			.oauthId(member.getOauthId())
			.loginType(member.getLoginType())
			.build();
	}
}
