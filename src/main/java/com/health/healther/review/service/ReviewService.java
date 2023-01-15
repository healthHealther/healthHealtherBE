package com.health.healther.review.service;

import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    void createReview(ReviewCreateRequestDto request);

    void deleteReview(Long reviewId);
}
