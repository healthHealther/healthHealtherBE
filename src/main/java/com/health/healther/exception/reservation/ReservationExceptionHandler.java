package com.health.healther.exception.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.health.healther.exception.member.MemberCustomException;
import com.health.healther.exception.member.MemberErrorResponse;

@RestControllerAdvice
public class ReservationExceptionHandler {

	@ExceptionHandler(value = MemberCustomException.class)
	protected ResponseEntity<MemberErrorResponse> handleCustomException(MemberCustomException e) {
		return MemberErrorResponse.toResponseEntity(e);
	}
}
