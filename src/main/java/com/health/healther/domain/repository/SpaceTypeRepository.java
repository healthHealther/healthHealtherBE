package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.SpaceType;

public interface SpaceTypeRepository extends JpaRepository<SpaceType, Long> {
}
