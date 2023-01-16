package com.health.healther.service;

import static com.health.healther.exception.coupon.CouponErrorCode.*;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.domain.model.Coupon;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.repository.CouponRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.dto.coupon.CouponCreateRequestDto;
import com.health.healther.dto.coupon.CouponResponseDto;
import com.health.healther.exception.coupon.CouponCustomException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {

	private final CouponRepository couponRepository;

	private final SpaceRepository spaceRepository;

	public void addCoupon(CouponCreateRequestDto createDto) {

		Space space = spaceRepository.findById(createDto.getSpaceId())
			.orElseThrow(() -> new CouponCustomException(NOT_FOUND_SPACE));

		Coupon coupon = Coupon.builder()
			.space(space)
			.discountAmount(createDto.getDiscountAmount())
			.openDate(createDto.getOpenDate())
			.expiredDate(createDto.getExpiredDate())
			.couponNumber(UUID.randomUUID().toString())
			.amount(createDto.getAmount())
			.isUsed(false)
			.build();
		couponRepository.save(coupon);

	}

	@Transactional
	public void deleteCoupon(Long couponId) {

		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new CouponCustomException(NOT_FOUND_COUPON));

		couponRepository.delete(coupon);

	}

	@Transactional(readOnly = true)
	public CouponResponseDto getCoupon(Long spaceId) {

		Optional<Coupon> coupon = couponRepository.findBySpace_Id(spaceId);
		if (!coupon.isPresent()) {
			throw new CouponCustomException(NOT_FOUND_SPACE);
		}

		return CouponResponseDto.builder()
			.couponId(coupon.get().getId())
			.memberName(coupon.get().getMember().getName())
			.discountAmount(coupon.get().getDiscountAmount())
			.openDate(coupon.get().getOpenDate())
			.expiredDate(coupon.get().getExpiredDate())
			.couponNumber(coupon.get().getCouponNumber())
			.amount(coupon.get().getAmount())
			.isUsed(coupon.get().isUsed())
			.build();
	}
}
