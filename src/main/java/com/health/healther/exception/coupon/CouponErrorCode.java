package com.health.healther.exception.coupon;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponErrorCode {

	NOT_FOUND_SPACE(HttpStatus.BAD_REQUEST, "일치하는 공간이 없습니다."),
	NOT_FOUND_COUPON(HttpStatus.BAD_REQUEST, "일치하는 쿠폰이 없습니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}
