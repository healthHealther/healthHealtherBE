package com.health.healther.dto.coupon;

import java.time.LocalDate;

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
	private LocalDate openDate;

	@NotNull
	private LocalDate expiredDate;

	@NotNull
	private int amount;

	@Builder
	public CouponCreateRequestDto(Long spaceId, int discountAmount, LocalDate openDate, LocalDate expiredDate,
			int amount) {
		this.spaceId = spaceId;
		this.discountAmount = discountAmount;
		this.openDate = openDate;
		this.expiredDate = expiredDate;
		this.amount = amount;
	}
}

