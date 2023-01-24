package com.health.healther.exception.reservation;

public class NotBusinessHoursException extends RuntimeException {
	public NotBusinessHoursException(String message) {
		super(message);
	}
}
