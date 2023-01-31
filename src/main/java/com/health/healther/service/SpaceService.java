package com.health.healther.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.healther.constant.ConvenienceType;
import com.health.healther.constant.SpaceType;
import com.health.healther.domain.model.Convenience;
import com.health.healther.domain.model.Image;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.model.SpaceKind;
import com.health.healther.domain.model.SpaceTime;
import com.health.healther.domain.repository.ConvenienceRepository;
import com.health.healther.domain.repository.ImageRepository;
import com.health.healther.domain.repository.SpaceKindRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.domain.repository.SpaceTimeRepository;
import com.health.healther.dto.coupon.CouponReservationListResponseDto;
import com.health.healther.dto.coupon.CouponCreateRequestDto;
import com.health.healther.dto.space.CreateSpaceRequestDto;
import com.health.healther.dto.space.SpaceDetailResponseDto;
import com.health.healther.dto.space.SpaceListRequestDto;
import com.health.healther.dto.space.SpaceListResponseDto;
import com.health.healther.exception.space.NotFoundSpaceException;
import com.health.healther.exception.space.NotMatchSpaceTypeException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpaceService {
	private final CouponService couponService;
	private final ReviewService reviewService;
	private final MemberService memberService;
	private final SpaceRepository spaceRepository;
	private final SpaceTimeRepository spaceTimeRepository;
	private final SpaceKindRepository spaceKindRepository;
	private final ConvenienceRepository convenienceRepository;
	private final ImageRepository imageRepository;

	@Transactional
	public Long saveSpaceInfo(
			CreateSpaceRequestDto createSpaceRequestDto,
			Member member
	) {
		// 1. 공간 등록
		Space space = Space.of(createSpaceRequestDto, member);

		spaceRepository.save(space);

		// 2. 예약 가능 시간 등록
		spaceTimeRepository.save(SpaceTime.of(space, createSpaceRequestDto));

		// 3. 공간 유형 등록
		spaceKindRepository.saveAll(
				createSpaceRequestDto.getSpaceTypes().stream()
						.map(spaceType -> SpaceKind.builder()
								.space(space)
								.spaceType(spaceType)
								.build())
						.collect(Collectors.toList())
		);

		// 4. 편의사항 등록
		convenienceRepository.saveAll(
				createSpaceRequestDto.getConvenienceTypes().stream()
						.map(convenienceType -> Convenience.builder()
								.space(space)
								.convenienceType(convenienceType)
								.build())
						.collect(Collectors.toList())
		);

		// 5. 이미지 등록
		imageRepository.saveAll(
				createSpaceRequestDto.getImages().stream()
						.map(url -> Image.builder()
								.space(space)
								.imageUrl(url)
								.build()
						).collect(Collectors.toList())
		);


		couponService.addCoupon(
				CouponCreateRequestDto.builder()
						.spaceId(space.getId())
						.discountAmount(createSpaceRequestDto.getDiscountAmount())
						.openDate(createSpaceRequestDto.getOpenDate())
						.expiredDate(createSpaceRequestDto.getExpiredDate())
						.amount(createSpaceRequestDto.getAmount())
						.build()
		);

		return space.getId();
	}

	public Long createSpace(CreateSpaceRequestDto createSpaceRequestDto) {
		validationSpaceType(createSpaceRequestDto.getSpaceTypes());
		validationConvenienceType(createSpaceRequestDto.getConvenienceTypes());

		Member member = memberService.findUserFromToken();

		return saveSpaceInfo(createSpaceRequestDto, member);
	}

	private void validationConvenienceType(Set<ConvenienceType> convenienceTypes) {
		for (ConvenienceType convenienceType : convenienceTypes) {
			if (convenienceType == null)
				throw new NotMatchSpaceTypeException("일치하는 편의사항이 없습니다.");
		}
	}

	private void validationSpaceType(Set<SpaceType> spaceTypes) {
		for (SpaceType spaceType : spaceTypes) {
			if (spaceType == null)
				throw new NotMatchSpaceTypeException("일치하는 공간 유형이 없습니다.");
		}
	}

	@Transactional(readOnly = true)
	public SpaceDetailResponseDto getSpaceDetail(Long spaceId) {
		Space space = spaceRepository.findByIdUseFetchJoin(spaceId)
				.orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));

		Set<SpaceType> spaceKinds = new HashSet<>(
				space.getSpaceKinds().stream()
						.map(spaceKind -> spaceKind.getSpaceType())
						.collect(Collectors.toList())
		);

		Set<ConvenienceType> conveniences = new HashSet<>(
				space.getConveniences().stream()
						.map(convenience -> convenience.getConvenienceType())
						.collect(Collectors.toList())
		);

		Set<String> images = new HashSet<>(
				space.getImages().stream()
						.map(image -> image.getImageUrl())
						.collect(Collectors.toList())
		);

		return SpaceDetailResponseDto.of(
				space,
				spaceKinds,
				conveniences,
				images
		);
	}

	public Page<SpaceListResponseDto> getSpaceList(SpaceListRequestDto spaceListRequestDto) {
		PageRequest pageRequest = PageRequest.of(
				spaceListRequestDto.getPage(),
				spaceListRequestDto.getSize(),
				Sort.Direction.DESC,
				"createdAt"
		);
		List<SpaceType> spaceTypes = List.of(SpaceType.GX, SpaceType.PILATES, SpaceType.ANAEROBIC, SpaceType.AEROBIC);

		if (spaceListRequestDto.getSpaceType() != null) {
			spaceTypes = new ArrayList<>(spaceListRequestDto.getSpaceType());
		}

		String searchText = "";
		if (spaceListRequestDto.getSearchText() != null) {
			searchText = spaceListRequestDto.getSearchText();
		}

		return spaceKindRepository.findAllBySpaceTypeIsInAndSpace_TitleContainingIgnoreCase(
						spaceTypes,
						pageRequest,
						searchText
				).map(spaceKind ->
					new SpaceListResponseDto(
							spaceKind,
							couponService.availableCouponIsExist(spaceKind.getSpace().getId()),
							reviewService.getReviewList(spaceKind.getSpace().getId())
					)
				);
	}
}
