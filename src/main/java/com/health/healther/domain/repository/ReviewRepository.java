package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
