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
import com.health.healther.domain.model.Space;
import com.health.healther.domain.model.SpaceKind;
import com.health.healther.domain.model.SpaceTime;
import com.health.healther.domain.repository.ConvenienceRepository;
import com.health.healther.domain.repository.ImageRepository;
import com.health.healther.domain.repository.SpaceKindRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.domain.repository.SpaceTimeRepository;
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
	private final SpaceRepository spaceRepository;
	private final SpaceTimeRepository spaceTimeRepository;
	private final SpaceKindRepository spaceKindRepository;
	private final ConvenienceRepository convenienceRepository;
	private final ImageRepository imageRepository;

	@Transactional
	public void saveSpaceInfo(CreateSpaceRequestDto createSpaceRequestDto) {
		// 1. 공간 등록
		Space space = Space.builder()
				// .member()
				.title(createSpaceRequestDto.getTitle())
				.content(createSpaceRequestDto.getContent())
				.address(createSpaceRequestDto.getAddress())
				.addressDetail(createSpaceRequestDto.getAddressDetail())
				.notice(createSpaceRequestDto.getNotice())
				.rule(createSpaceRequestDto.getRule())
				.price(createSpaceRequestDto.getPrice())
				.build();

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

	}

	public void createSpace(CreateSpaceRequestDto createSpaceRequestDto) {
		validationSpaceType(createSpaceRequestDto.getSpaceTypes());
		validationConvenienceType(createSpaceRequestDto.getConvenienceTypes());

		// TODO Find the member and set it up.
		saveSpaceInfo(createSpaceRequestDto);
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

		return SpaceDetailResponseDto.builder()
				.spaceId(space.getId())
				.title(space.getTitle())
				.content(space.getContent())
				.address(space.getAddress())
				.addressDetail(space.getAddressDetail())
				.convenienceTypes(conveniences)
				.notice(space.getNotice())
				.rule(space.getRule())
				.price(space.getPrice())
				.images(images)
				.openTime(space.getSpaceTime().getOpenTime())
				.closeTime(space.getSpaceTime().getCloseTime())
				.spaceTypes(spaceKinds)
				.build();
	}

	public Page<SpaceListResponseDto> getSpaceList(SpaceListRequestDto spaceListRequestDto) {
		PageRequest pageRequest = PageRequest.of(
				spaceListRequestDto.getPage(),
				spaceListRequestDto.getSize(),
				Sort.Direction.DESC,
				"createdAt"
		);

		List<SpaceType> spaceTypes = new ArrayList<>(spaceListRequestDto.getSpaceType());

		return spaceKindRepository.findAllBySpaceTypeIsIn(spaceTypes, pageRequest)
				.map(spaceKind ->
						new SpaceListResponseDto(spaceKind)
				);
	}
}
