package com.health.healther.exception.coupon;

public class NotExistCouponInReservationException extends RuntimeException {
	public NotExistCouponInReservationException(String message) {
		super(message);
	}
}
