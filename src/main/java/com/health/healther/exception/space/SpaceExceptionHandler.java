package com.health.healther.exception.space;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.health.healther.controller.SpaceController;
import com.health.healther.exception.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackageClasses = SpaceController.class)
public class SpaceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> MethodArgumentNotValidException(
			MethodArgumentNotValidException exception
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

	@ExceptionHandler(NotMatchConvenienceTypeException.class)
	public ResponseEntity<ErrorMessage> NotMatchConvenienceTypeException(
			NotMatchConvenienceTypeException exception
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

	@ExceptionHandler(NotFoundConvenienceTypeException.class)
	public ResponseEntity<ErrorMessage> NotFoundConvenienceTypeException(
			NotFoundConvenienceTypeException exception
	) {
		return ResponseEntity.badRequest()
				.body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
	}
}
