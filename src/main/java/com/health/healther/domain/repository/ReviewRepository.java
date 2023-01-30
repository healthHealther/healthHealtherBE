package com.health.healther.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.health.healther.domain.model.Review;
import com.health.healther.domain.model.Space;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
