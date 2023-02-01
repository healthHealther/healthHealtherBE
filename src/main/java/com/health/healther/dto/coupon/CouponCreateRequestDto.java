package com.health.healther.dto.coupon;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CouponCreateRequestDto {
	@NotNull
	private Long spaceId;

	@NotNull
	private int discountAmount;

	@NotNull
	private String openDate;

	@NotNull
	private String expiredDate;

	@NotNull
	private int amount;

	@Builder
	public CouponCreateRequestDto(Long spaceId, int discountAmount, String openDate, String expiredDate,
		int amount) {
		this.spaceId = spaceId;
		this.discountAmount = discountAmount;
		this.openDate = openDate;
		this.expiredDate = expiredDate;
		this.amount = amount;
	}
}

