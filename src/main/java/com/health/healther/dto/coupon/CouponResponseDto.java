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
public class CouponResponseDto {

	private Long couponId;
	private String memberName;
	private int discountAmount;
	private LocalDate openDate;
	private LocalDate expiredDate;
	private String couponNumber;
	private int amount;
	private boolean isUsed;
}
