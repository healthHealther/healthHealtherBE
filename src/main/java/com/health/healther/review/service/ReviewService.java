package com.health.healther.review.service;

import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import com.health.healther.review.domain.dto.ReviewDto;

import com.health.healther.review.domain.dto.ReviewRequestUpdateDto;
import java.util.List;

import java.util.List;

public interface ReviewService {
    void createReview(ReviewCreateRequestDto request);

    void deleteReview(Long reviewId);

    List<ReviewDto> getReviewList(Long spaceId);

    void updateReview(ReviewRequestUpdateDto dto, Long reviewId);


}
