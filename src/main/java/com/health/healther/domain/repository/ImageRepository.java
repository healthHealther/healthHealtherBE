package com.health.healther.domain.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	Optional<Set<Image>> findAllBySpaceId(Long spaceId);
}
