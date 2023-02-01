package com.health.healther.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.domain.model.Coupon;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.repository.CouponRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.dto.coupon.CouponCreateRequestDto;
import com.health.healther.dto.coupon.CouponReservationListResponseDto;
import com.health.healther.dto.coupon.CouponUpdateRequestDto;
import com.health.healther.exception.coupon.AlreadyDownloadCouponException;
import com.health.healther.exception.coupon.AlreadySoldOutCouponException;
import com.health.healther.exception.coupon.NotFoundCouponException;
import com.health.healther.exception.coupon.NotUsedCouponException;
import com.health.healther.exception.space.NotFoundSpaceException;
import com.health.healther.util.DateTimeUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
	private final CouponRepository couponRepository;

	private final SpaceRepository spaceRepository;

	private final MemberService memberService;

	public void addCoupon(CouponCreateRequestDto couponCreateRequestDto) {
		Space space = spaceRepository.findById(couponCreateRequestDto.getSpaceId())
			.orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));

		List<Coupon> coupons = new ArrayList<>();
		int amount = couponCreateRequestDto.getAmount();
		for (int i = 0; i < amount; i++) {
			Coupon coupon = Coupon.builder()
				.space(space)
				.discountAmount(couponCreateRequestDto.getDiscountAmount())
				.openDate(DateTimeUtil.getLocalDate(couponCreateRequestDto.getOpenDate()))
				.expiredDate(DateTimeUtil.getLocalDate(couponCreateRequestDto.getExpiredDate()))
				.couponNumber(UUID.randomUUID().toString())
				.isUsed(false)
				.build();
			coupons.add(coupon);
		}
		couponRepository.saveAll(coupons);
	}

	@Transactional
	public void deleteCoupon(Long couponId) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new NotFoundCouponException("쿠폰 정보를 찾을 수 없습니다."));

		couponRepository.delete(coupon);
	}

	@Transactional(readOnly = true)
	public List<CouponReservationListResponseDto> getCoupon(Long spaceId) {
		Member member = memberService.findUserFromToken();

		LocalDate expiredDt = LocalDate.now().minusDays(1);
		LocalDate openDt = LocalDate.now().plusDays(1);

		List<Coupon> couponList = couponRepository
			.findBySpace_IdAndMember_IdAndExpiredDateIsAfterAndOpenDateIsBeforeAndIsUsed(
				spaceId, member.getId(), expiredDt, openDt, false
			);

		if (couponList.size() == 0) {
			throw new NotFoundCouponException("쿠폰 정보를 찾을 수 없습니다.");
		}

		return couponList.stream()
			.map(CouponReservationListResponseDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public void updateCoupon(Long couponId, CouponUpdateRequestDto couponUpdateRequestDto) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new NotFoundCouponException("쿠폰 정보를 찾을 수 없습니다."));

		coupon.updateCoupon(couponUpdateRequestDto.getDiscountAmount(),
			couponUpdateRequestDto.getOpenDate(),
			couponUpdateRequestDto.getExpiredDate());
	}

	@Transactional
	public void useCoupon(Long couponId) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new NotFoundCouponException("쿠폰 정보를 찾을 수 없습니다."));

		coupon.useCoupon(true);
	}

	@Transactional
	public void cancelUseCoupon(Long couponId) {
		Coupon coupon = couponRepository.findById(couponId)
			.orElseThrow(() -> new NotFoundCouponException("쿠폰 정보를 찾을 수 없습니다."));

		if (!coupon.isUsed()) {
			throw new NotUsedCouponException("사용되지 않은 쿠폰입니다.");
		}

		coupon.useCoupon(false);
	}

	@Transactional
	public void downloadCoupon(Long spaceId) {
		Member member = memberService.findUserFromToken();

		LocalDate expiredDt = LocalDate.now().minusDays(1);
		LocalDate openDt = LocalDate.now().plusDays(1);

		List<Coupon> couponList = couponRepository
			.findBySpace_IdAndMember_IdAndExpiredDateIsAfterAndOpenDateIsBefore(
				spaceId, member.getId(), expiredDt, openDt
			);

		if (!couponList.isEmpty()) {
			throw new AlreadyDownloadCouponException("이미 발급된 쿠폰입니다.");
		}
		Coupon coupon = couponRepository
			.findTopBySpace_IdAndMember_IdAndExpiredDateIsAfterAndOpenDateIsBeforeAndIsUsed(
				spaceId, null, expiredDt, openDt, false
			).orElseThrow(() -> new AlreadySoldOutCouponException("쿠폰이 모두 소진 되었습니다."));
		coupon.downloadCoupon(member);
	}

	public boolean availableCouponIsExist(Long spaceId) {
		return couponRepository.existsBySpace_IdAndIsUsed(spaceId, false);
	}
}

