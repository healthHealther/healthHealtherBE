package com.health.healther.exception.coupon;

import lombok.Getter;

@Getter
public class CouponCustomException extends RuntimeException {

	private final CouponErrorCode couponErrorCode;

	public CouponCustomException(CouponErrorCode couponErrorCode) {
		super(couponErrorCode.getDetail());
		this.couponErrorCode = couponErrorCode;
	}
}
