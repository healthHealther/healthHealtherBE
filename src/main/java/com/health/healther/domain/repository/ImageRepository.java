package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
