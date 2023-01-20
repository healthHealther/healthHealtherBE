package com.health.healther.dto.coupon;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CouponReservationResponseDto {
	private Long couponId;

	private int discountAmount;

	private LocalDate expiredDate;

	private String couponNumber;

	private boolean isUsed;
}