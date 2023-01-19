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

	// @Query("select s from Space s join fetch s.spaceTime join fetch s.spaceKinds join fetch s.conveniences join fetch s.images "
	// 		+ "where s.title like %:title%")
	// @EntityGraph(attributePaths = {"spaceTime", "spaceKinds", "conveniences", "images"})
	// @Query("select s from Space s left join s.spaceTime left join s.spaceKinds left join s.conveniences left join s.images "
	// 		+ "where s.title like %:title%")
	// List<Space> findAllByIdUseFetchJoin(String title, Pageable pageable);

	// Page<Space> findAllByTitleContainingIgnoreCase(String title);
}
