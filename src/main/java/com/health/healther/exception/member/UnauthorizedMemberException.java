package com.health.healther.exception.member;

public class UnauthorizedMemberException extends RuntimeException {
	public UnauthorizedMemberException(String message) {
		super(message);
	}
}
