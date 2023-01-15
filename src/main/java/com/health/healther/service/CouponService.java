package com.health.healther.service;

import static com.health.healther.exception.coupon.CouponErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.domain.model.Coupon;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.repository.CouponRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.dto.coupon.CouponCreateDto;
import com.health.healther.exception.coupon.CouponCustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	private final SpaceRepository spaceRepository;

	@Transactional
	public Long addCoupon(CouponCreateDto createDto) {

		Space space = spaceRepository.findById(createDto.getSpace().getId())
			.orElseThrow(() -> new CouponCustomException(NOT_FOUND_SPACE));

		couponRepository.save(Coupon.builder()
			.space(space)
			.discountAmount(createDto.getDiscountAmount())
			.openDate(createDto.getOpenDate())
			.expiredDate(createDto.getExpiredDate())
			.couponNumber(createDto.getCouponNumber())
			.amount(createDto.getAmount())
			.build());

		return createDto.getSpace().getId();

	}
}
