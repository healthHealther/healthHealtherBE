package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
