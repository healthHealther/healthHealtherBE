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

	public String addCoupon(CouponCreateDto createDto) {

		Space space = spaceRepository.findById(createDto.getSpaceId())
			.orElseThrow(() -> new CouponCustomException(NOT_FOUND_SPACE));

		Coupon coupon = Coupon.builder()
			.space(space)
			.discountAmount(createDto.getDiscountAmount())
			.openDate(createDto.getOpenDate())
			.expiredDate(createDto.getExpiredDate())
			.couponNumber(createDto.getCouponNumber())
			.amount(createDto.getAmount())
			.build();
		couponRepository.save(coupon);

		return coupon.getSpace().getTitle();

	}

	@Transactional
	public void deleteCoupon(Long couponId) {

		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new CouponCustomException(NOT_FOUND_COUPON));

		couponRepository.delete(coupon);

	}
}
