package com.health.healther.exception.reservation;

import lombok.Getter;

@Getter
public class ReservationCustomException extends RuntimeException {

	private final ReservationErrorCode errorCode;

	public ReservationCustomException(ReservationErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ReservationCustomException(ReservationErrorCode errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
}
