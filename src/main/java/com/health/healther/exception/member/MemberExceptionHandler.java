package com.health.healther.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.health.healther.controller.MemberController;
import com.health.healther.exception.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {
	@ExceptionHandler(InvalidAccessException.class)
	public ResponseEntity<ErrorMessage> InvalidAccessException(
		InvalidAccessException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<ErrorMessage> InvalidTokenException(
		InvalidTokenException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotMatchMemberException.class)
	public ResponseEntity<ErrorMessage> ForbiddenMemberException(
		NotMatchMemberException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(NotFoundMemberException.class)
	public ResponseEntity<ErrorMessage> NotFoundMemberException(
		NotFoundMemberException exception
	) {
		return ResponseEntity.badRequest()
			.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
