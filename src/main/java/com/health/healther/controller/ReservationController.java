package com.health.healther.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.reservation.AvailableTimeResponseDto;
import com.health.healther.dto.reservation.MakeReservationRequestDto;
import com.health.healther.dto.reservation.ReservationListResponseDto;
import com.health.healther.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
	private final ReservationService reservationService;

	@GetMapping("/available/{spaceId}")
	public ResponseEntity<List<AvailableTimeResponseDto>> getAvailableTime(
		@PathVariable Long spaceId,
		@RequestParam String reserveDate
	) {
		return ResponseEntity.ok(reservationService.getAvailableTimeResponseDto(spaceId, reserveDate));
	}

	@PostMapping
	public ResponseEntity<Long> makeReservation(
		@RequestBody @Valid MakeReservationRequestDto form
	) {
		return new ResponseEntity<>(
			reservationService.makeReservation(form), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Map<LocalDate, List<ReservationListResponseDto>>> getAllReservations() {
		return ResponseEntity.ok(reservationService.getReservations());
	}
}
