package com.health.healther.exception.reservation;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReservationErrorCode {

	NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),

	INAPPROPRIATE_DATE(HttpStatus.BAD_REQUEST, "예약 날짜는 현재 날짜보다 이후여야 합니다."),
	ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "이미 예약된 시간입니다.");

	private final HttpStatus httpStatus;
	private final String description;
}
