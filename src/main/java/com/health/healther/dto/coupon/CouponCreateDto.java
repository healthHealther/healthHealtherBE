package com.health.healther.dto.coupon;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.health.healther.domain.model.Space;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CouponCreateDto {

	@NotNull
	private Space space;

	@NotNull
	private int discountAmount;

	@NotNull
	private LocalDate openDate;

	@NotNull
	private LocalDate expiredDate;

	@NotBlank
	private String couponNumber;

	@NotNull
	private int amount;

}

