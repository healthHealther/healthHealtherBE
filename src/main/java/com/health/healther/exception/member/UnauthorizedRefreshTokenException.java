package com.health.healther.exception.member;

public class UnauthorizedRefreshTokenException extends RuntimeException {
	public UnauthorizedRefreshTokenException(String message) {
		super(message);
	}
}
