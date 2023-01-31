package com.health.healther.domain.repository;

import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import com.health.healther.domain.model.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByMemberAndSpace(Member member, Space space);
}
