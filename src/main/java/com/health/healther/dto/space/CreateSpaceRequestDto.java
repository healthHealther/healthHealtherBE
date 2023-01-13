package com.health.healther.dto.space;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateSpaceRequestDto {
	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@NotBlank
	private String address;

	private String addressDetail;

	@NotEmpty
	@Valid
	private List<ConvenienceTypeDto> convenienceTypes;

	@NotBlank
	private String notice;

	@NotBlank
	private String rule;

	@Min(1000)
	private int price;

	@Valid
	private List<ImageUrlDto> images;

	@Min(00)
	private int openTime;

	@Max(24)
	private int closeTime;

	@NotEmpty
	@Valid
	private List<SpaceTypeDto> spaceTypes;
}
