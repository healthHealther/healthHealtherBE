package com.health.healther.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.healther.domain.model.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
	@Query("select s from Space s"
			+ " join fetch s.spaceTime"
			+ " join fetch s.spaceKinds"
			+ " join fetch s.conveniences"
			+ " join fetch s.images"
			+ " where s.id = :spaceId")
	Optional<Space> findByIdUseFetchJoin(Long spaceId);
}
