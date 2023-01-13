package com.health.healther.review.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.review.domain.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
