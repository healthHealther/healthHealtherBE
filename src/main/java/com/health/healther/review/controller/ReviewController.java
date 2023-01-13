package com.health.healther.review.controller;

import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import com.health.healther.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    public void createReview(
            @RequestBody @Valid ReviewCreateRequestDto request
    ) {
        reviewService.createReview(request);
    }
}
