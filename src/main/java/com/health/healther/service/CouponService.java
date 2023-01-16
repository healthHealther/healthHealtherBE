package com.health.healther.service;

import static com.health.healther.exception.coupon.CouponErrorCode.*;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.health.healther.domain.model.Coupon;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.repository.CouponRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.dto.coupon.CouponCreateRequestDto;
import com.health.healther.exception.coupon.CouponCustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	private final SpaceRepository spaceRepository;

	public String addCoupon(CouponCreateRequestDto createDto) {

		Space space = spaceRepository.findById(createDto.getSpaceId())
			.orElseThrow(() -> new CouponCustomException(NOT_FOUND_SPACE));

		Coupon coupon = Coupon.builder()
			.space(space)
			.discountAmount(createDto.getDiscountAmount())
			.openDate(createDto.getOpenDate())
			.expiredDate(createDto.getExpiredDate())
			.couponNumber(UUID.randomUUID().toString())
			.amount(createDto.getAmount())
			.build();
		couponRepository.save(coupon);

		return coupon.getSpace().getTitle();

	}
}
