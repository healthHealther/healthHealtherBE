package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.SpaceKind;

public interface SpaceKindRepository extends JpaRepository<SpaceKind, Long> {
}
