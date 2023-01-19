package com.health.healther.dto.space;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.health.healther.constant.SpaceType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SpaceListRequestDto {

	@NotNull
	private Integer page;

	@NotNull
	private Integer size;

	private String searchText;

	private Set<SpaceType> spaceType;
}
