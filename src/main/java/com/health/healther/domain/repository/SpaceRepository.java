package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
}
