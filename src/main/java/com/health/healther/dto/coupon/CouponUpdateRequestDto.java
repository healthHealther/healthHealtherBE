package com.health.healther.dto.coupon;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CouponUpdateRequestDto {
	@NotNull
	private int discountAmount;

	@NotNull
	private String openDate;

	@NotNull
	private String expiredDate;

}
