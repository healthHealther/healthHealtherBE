package com.health.healther.exception.member;

public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException(String message) {
		super(message);
	}
}
