package com.health.healther.exception.coupon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.health.healther.controller.CouponController;
import com.health.healther.exception.ErrorMessage;
import com.health.healther.exception.space.NotMatchSpaceTypeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = CouponController.class)
public class CouponExceptionHandler {

	@ExceptionHandler(NotFoundCouponException.class)
	public ResponseEntity<ErrorMessage> NotFoundCouponException(
		NotFoundCouponException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotMatchSpaceTypeException.class)
	public ResponseEntity<ErrorMessage> NotMatchSpaceTypeException(
		NotMatchSpaceTypeException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotUsedCouponException.class)
	public ResponseEntity<ErrorMessage> NotUseCouponException(
		NotUsedCouponException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(AlreadyDownloadCouponException.class)
	public ResponseEntity<ErrorMessage> AlreadyDownloadCouponException(
		AlreadyDownloadCouponException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(AlreadySoldOutCouponException.class)
	public ResponseEntity<ErrorMessage> AlreadySoldOutCouponException(
		AlreadySoldOutCouponException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

}
