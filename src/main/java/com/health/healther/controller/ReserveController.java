package com.health.healther.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.dto.reservation.AvailableTimeResponse;
import com.health.healther.dto.reservation.MakeReservationForm;
import com.health.healther.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReserveController {
	private final ReservationService reservationService;

	@PostMapping("/reserve")
	public ResponseEntity makeReservation(@RequestBody @Valid MakeReservationForm form) {
		reservationService.makeReservation(form);
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@GetMapping("/reserve/available/{spaceId}")
	public ResponseEntity<AvailableTimeResponse> getAvailableTime(@PathVariable Long spaceId, @RequestParam String reserveDate) {
		return ResponseEntity.ok(AvailableTimeResponse.from(reservationService.getAvailableTime(spaceId, reserveDate)));
	}
}
