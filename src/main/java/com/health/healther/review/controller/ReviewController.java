package com.health.healther.review.controller;

import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import com.health.healther.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<?> createReview(
            @RequestBody @Valid ReviewCreateRequestDto request
    ) {

        reviewService.createReview(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable("reviewId") Long reviewId
    ) {

        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<?> getReviewList(
            @PathVariable("spaceId") Long spaceId
    ) {

        return new ResponseEntity<>(
                reviewService.getReviewList(spaceId),HttpStatus.OK
        );
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(
            @RequestBody @Valid ReviewRequestUpdateDto request,
            @PathVariable("reviewId") Long reviewId
    ) {

        reviewService.updateReview(request, reviewId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

}
