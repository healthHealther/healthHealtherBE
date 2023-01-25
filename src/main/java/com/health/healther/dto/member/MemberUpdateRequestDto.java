package com.health.healther.dto.member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class MemberUpdateRequestDto {
	@NotNull
	private String name;

	@NotNull
	private String nickName;

	@NotNull
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
	private String phone;
}
