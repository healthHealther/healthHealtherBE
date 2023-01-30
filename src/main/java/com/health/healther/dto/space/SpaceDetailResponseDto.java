package com.health.healther.dto.space;

import java.util.Set;

import com.health.healther.constant.ConvenienceType;
import com.health.healther.constant.SpaceType;
import com.health.healther.domain.model.Space;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class SpaceDetailResponseDto {
	private Long spaceId;

	private String title;

	private String content;

	private String address;

	private String addressDetail;

	private Set<ConvenienceType> convenienceTypes;

	private String notice;

	private String rule;

	private int price;

	private Set<String> images;

	private int openTime;

	private int closeTime;

	private Set<SpaceType> spaceTypes;

	public static SpaceDetailResponseDto of(
			Space space,
			Set<SpaceType> spaceKinds,
			Set<ConvenienceType> conveniences,
			Set<String> images
	) {
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
}
