package com.health.healther.exception.member;

import lombok.Getter;

@Getter
public class MemberCustomException extends RuntimeException {
	private final MemberErrorCode errorCode;

	public MemberCustomException(MemberErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public MemberCustomException(MemberErrorCode errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
}
