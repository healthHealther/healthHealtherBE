package com.health.healther.dto.reservation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AvailableTimeResponseDto {
	private int time;

	public static AvailableTimeResponseDto from(int time) {
		return AvailableTimeResponseDto.builder()
			.time(time)
			.build();
	}
}
