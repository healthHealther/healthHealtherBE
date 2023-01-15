package com.health.healther.review.service;

import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import com.health.healther.review.domain.dto.ReviewDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewCreateRequestDto request);

    void deleteReview(Long reviewId);

    List<ReviewDto> getReviewList(Long spaceId);
}
