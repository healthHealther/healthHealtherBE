package com.health.healther.exception.coupon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CouponExceptionHandler {

	@ExceptionHandler({CouponCustomException.class})
	public ResponseEntity<ExceptionResponse> customRequestException(final CouponCustomException customException) {
		log.warn("api Exception : {}", customException.getCouponErrorCode());
		return ResponseEntity.badRequest()
			.body(new ExceptionResponse(customException.getMessage(), customException.getCouponErrorCode()));
	}

	@Getter
	@ToString
	@AllArgsConstructor
	public static class ExceptionResponse {
		private String message;
		private CouponErrorCode couponErrorCode;
	}
}
