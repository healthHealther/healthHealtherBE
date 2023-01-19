package com.health.healther.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.SpaceTime;

public interface SpaceTimeRepository extends JpaRepository<SpaceTime, Long> {
	Optional<SpaceTime> findBySpaceId(Long spaceId);
}
