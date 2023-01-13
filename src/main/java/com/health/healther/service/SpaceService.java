package com.health.healther.service;

import java.util.Arrays;

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
import com.health.healther.exception.space.NotMatchConvenienceTypeException;
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
	public void createSpace(CreateSpaceRequestDto createSpaceRequestDto) {
		// TODO Find the member and set it up.

		// 1. 공간 등록
		Space space = spaceRepository.save(Space.of(createSpaceRequestDto));

		// 2. 예약 가능 시간 등록
		spaceTimeRepository.save(SpaceTime.of(space, createSpaceRequestDto));

		// 3. 공간 유형 등록
		createSpaceRequestDto.getSpaceTypes().stream()
				.forEach(spaceTypeDto -> {
					SpaceType spaceType = Arrays.stream(SpaceType.values())
							.filter(type -> type.name().equals(spaceTypeDto.getSpaceType()))
							.findFirst()
							.orElseThrow(() -> new NotMatchSpaceTypeException("일치하는 공간 유형이 없습니다."));

					spaceKindRepository.save(SpaceKind.of(space, spaceType));
				});

		// 4. 편의사항 등록
		createSpaceRequestDto.getConvenienceTypes().stream()
				.forEach(convenienceTypeDto -> {
					ConvenienceType convenienceType = Arrays.stream(ConvenienceType.values())
							.filter(type -> type.name().equals(convenienceTypeDto.getConvenienceType()))
							.findFirst()
							.orElseThrow(() -> new NotMatchConvenienceTypeException("일치하는 편의사항이 없습니다"));

					convenienceRepository.save(Convenience.of(space, convenienceType));

				});

		// 5. 이미지 등록
		createSpaceRequestDto.getImages().stream()
				.forEach(imageUrlDto -> {
					imageRepository.save(Image.of(space, imageUrlDto.getUrl()));
				});
	}
}
