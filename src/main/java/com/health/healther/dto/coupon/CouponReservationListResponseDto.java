package com.health.healther.dto.coupon;

import java.time.LocalDate;

import com.health.healther.domain.model.Coupon;

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
public class CouponReservationListResponseDto {
	private Long couponId;

	private int discountAmount;

	private LocalDate expiredDate;

	private String couponNumber;

	private boolean isUsed;

	public static CouponReservationListResponseDto from(Coupon coupon) {
		return CouponReservationListResponseDto.builder()
			.couponId(coupon.getId())
			.discountAmount(coupon.getDiscountAmount())
			.expiredDate(coupon.getExpiredDate())
			.couponNumber(coupon.getCouponNumber())
			.isUsed(coupon.isUsed())
			.build();
	}
}
