package com.health.healther.exception.reservation;

public class AlreadyReservedException extends RuntimeException {
	public AlreadyReservedException(String message) {
		super(message);
	}
}
