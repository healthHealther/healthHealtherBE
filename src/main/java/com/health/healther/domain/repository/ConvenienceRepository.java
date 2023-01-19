package com.health.healther.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Convenience;

public interface ConvenienceRepository extends JpaRepository<Convenience, Long> {
	Optional<Set<Convenience>> findAllBySpaceId(Long spaceId);
}
