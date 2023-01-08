package com.health.healther.member.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
	ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
	INVALID_ACCESS(HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),
	NOT_FOUND_MEMBER(HttpStatus.BAD_REQUEST, "존재하지 않는 회원입니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
