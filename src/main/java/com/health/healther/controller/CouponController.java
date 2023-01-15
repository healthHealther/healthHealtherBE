package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.coupon.CouponCreateDto;
import com.health.healther.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

	private final CouponService couponService;

	@PostMapping
	public Long addCoupon(@RequestBody @Valid CouponCreateDto createDto) {
		return couponService.addCoupon(createDto);
	}

}
