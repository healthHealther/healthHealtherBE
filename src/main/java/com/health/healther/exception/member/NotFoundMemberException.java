package com.health.healther.exception.member;

public class NotFoundMemberException extends RuntimeException {
	public NotFoundMemberException(String message) {
		super(message);
	}
}
