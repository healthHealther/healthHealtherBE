package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.coupon.CouponCreateRequestDto;
import com.health.healther.dto.coupon.CouponUpdateRequestDto;
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

	@DeleteMapping("/{couponId}")
	public ResponseEntity deleteCoupon(@PathVariable("couponId") Long couponId) {
		couponService.deleteCoupon(couponId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/{spaceId}")
	public ResponseEntity getCoupon(@PathVariable("spaceId") Long spaceId) {
		return ResponseEntity.ok().body(couponService.getCoupon(spaceId));
	}

	@PutMapping("/{couponId}")
	public ResponseEntity updateCoupon(@PathVariable("couponId") Long couponId,
		@RequestBody @Valid CouponUpdateRequestDto couponUpdateRequestDto) {
		couponService.updateCoupon(couponId, couponUpdateRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
