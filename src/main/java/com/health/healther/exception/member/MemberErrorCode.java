package com.health.healther.exception.member;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),

	INVALID_ACCESS(HttpStatus.BAD_REQUEST, "잘못된 접근입니다."),
	INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 토큰입니다."),

	UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다"),
	UNAUTHORIZED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access token이 유효하지 않습니다."),
	UNAUTHORIZED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh token이 유효하지 않습니다."),

	FORBIDDEN_USER(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다");

	private final HttpStatus httpStatus;
	private final String description;
}
