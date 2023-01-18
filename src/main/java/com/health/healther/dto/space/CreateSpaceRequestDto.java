package com.health.healther.dto.space;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.health.healther.constant.ConvenienceType;
import com.health.healther.constant.SpaceType;

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
	private Set<ConvenienceType> convenienceTypes;

	@NotBlank
	private String notice;

	@NotBlank
	private String rule;

	@Min(1000)
	private int price;

	@Valid
	private Set<String> images;

	@Min(00)
	private int openTime;

	@Max(24)
	private int closeTime;

	@NotEmpty
	@Valid
	private Set<SpaceType> spaceTypes;
}
