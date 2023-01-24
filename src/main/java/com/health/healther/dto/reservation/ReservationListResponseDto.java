package com.health.healther.dto.reservation;

import com.health.healther.domain.model.Reservation;

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
public class ReservationListResponseDto {
	String spaceName;
	int reservationTime;
	int price;

	public static ReservationListResponseDto from(Reservation reservation) {
		return ReservationListResponseDto.builder()
			.spaceName(reservation.getSpace().getTitle())
			.reservationTime(reservation.getReservationTime())
			.price(reservation.getPrice())
			.build();
	}
}
