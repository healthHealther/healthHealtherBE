package com.health.healther.dto.reservation;

import java.util.List;

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
public class AvailableTimeResponse {
	private List<Integer> time;

	public static AvailableTimeResponse from(List<Integer> time) {
		return AvailableTimeResponse.builder()
			.time(time)
			.build();
	}
}
