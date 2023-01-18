package com.health.healther.exception.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

	@ExceptionHandler(value = MemberCustomException.class)
	protected ResponseEntity<MemberErrorResponse> handleCustomException(MemberCustomException e) {
		return MemberErrorResponse.toResponseEntity(e);
	}
}
