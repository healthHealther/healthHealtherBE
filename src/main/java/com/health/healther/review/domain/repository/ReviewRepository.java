package com.health.healther.review.domain.repository;

import com.health.healther.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
