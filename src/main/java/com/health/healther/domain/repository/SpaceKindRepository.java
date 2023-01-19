package com.health.healther.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.SpaceKind;

public interface SpaceKindRepository extends JpaRepository<SpaceKind, Long> {
	Optional<Set<SpaceKind>> findAllBySpaceId(Long spaceId);
}
