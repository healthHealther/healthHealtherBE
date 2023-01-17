package com.health.healther.dto.coupon;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CouponGiftRequestDto {

	@NotNull
	private Long couponId;

	@NotNull
	private Long memberId;
}
