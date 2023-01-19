package com.health.healther.dto.reservation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class MakeReservationRequest {
	@NotNull
	@Min(0)
	private Long spaceId;

	private Long couponId;

	@NotBlank
	@Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "1905-01-01 형식으로 입력해주세요.")
	private String reservationDate;

	@Min(0)
	@Max(24)
	private int reservationTime;
}
