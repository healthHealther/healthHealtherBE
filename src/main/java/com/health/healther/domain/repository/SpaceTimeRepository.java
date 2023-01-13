package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.SpaceTime;

public interface SpaceTimeRepository extends JpaRepository<SpaceTime, Long> {
}
