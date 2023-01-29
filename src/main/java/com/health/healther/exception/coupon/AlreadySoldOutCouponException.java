package com.health.healther.exception.coupon;

public class AlreadySoldOutCouponException extends RuntimeException {
	public AlreadySoldOutCouponException(String message) {
		super(message);
	}
}
