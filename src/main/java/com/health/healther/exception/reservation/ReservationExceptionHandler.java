package com.health.healther.exception.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.health.healther.controller.MemberController;
import com.health.healther.exception.ErrorMessage;
import com.health.healther.exception.space.NotFoundSpaceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class ReservationExceptionHandler {
	@ExceptionHandler(AlreadyReservedException.class)
	public ResponseEntity<ErrorMessage> AlreadyReservedException(
		AlreadyReservedException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(InappropriateDateException.class)
	public ResponseEntity<ErrorMessage> InappropriateDateException(
		InappropriateDateException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotFoundSpaceException.class)
	public ResponseEntity<ErrorMessage> NotFoundSpaceException(
		NotFoundSpaceException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
