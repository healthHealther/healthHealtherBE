package com.health.healther.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.constant.SpaceType;
import com.health.healther.domain.model.SpaceKind;

public interface SpaceKindRepository extends JpaRepository<SpaceKind, Long> {
	Optional<Set<SpaceKind>> findAllBySpaceId(Long spaceId);

	@EntityGraph(attributePaths = {"space"})
	Page<SpaceKind> findAllBySpaceTypeIsInAndSpace_TitleContainingIgnoreCase(
			List<SpaceType> spaceType,
			Pageable pageable,
			String title
	);
}
