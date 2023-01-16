package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<String> addCoupon(@RequestBody @Valid CouponCreateDto createDto) {
		return ResponseEntity.ok(couponService.addCoupon(createDto));
	}

	@DeleteMapping("/{couponId}")
	public ResponseEntity deleteCoupon(@PathVariable("couponId") Long couponId) {
		couponService.deleteCoupon(couponId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
