package com.health.healther.dto.board;

import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardSearchListRequestDto {
	@NotNull
	private Integer page;

	@NotNull
	private Integer size;

	@NotNull
	private String keyword;
}