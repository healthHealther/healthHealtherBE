package com.health.healther.exception.member;

public class UnauthorizedAccessTokenException extends RuntimeException {
	public UnauthorizedAccessTokenException(String message) {
		super(message);
	}
}
