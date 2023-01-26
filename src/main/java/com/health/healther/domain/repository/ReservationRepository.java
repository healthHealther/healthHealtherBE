package com.health.healther.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	Optional<Reservation> findByReservationDateAndReservationTime(LocalDate reserveDate, int reserveTime);

	List<Reservation> findByMember_IdOrderByReservationDate(Long memberId);

	List<Reservation> findAllByMember_IdAndReservationDateOrderByReservationTime(
		Long memberId,
		LocalDate reservationDate
	);
}
