package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.coupon.CouponCreateRequestDto;
import com.health.healther.service.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponController {

	private final CouponService couponService;

	@PostMapping
	public ResponseEntity addCoupon(@RequestBody @Valid CouponCreateRequestDto createDto) {
		couponService.addCoupon(createDto);
		return ResponseEntity.ok().build();
	}

}
