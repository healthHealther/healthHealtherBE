package com.health.healther.service;


import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Review;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.domain.repository.ReviewRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.dto.review.ReviewCreateRequestDto;
import com.health.healther.dto.review.ReviewDto;
import com.health.healther.dto.review.ReviewRequestUpdateDto;
import com.health.healther.exception.review.NotFoundReviewException;
import com.health.healther.exception.space.NotFoundSpaceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final SpaceRepository spaceRepository;


    private final MemberRepository memberRepository;

    private final MemberService memberService;

    @Transactional
    public void createReview(ReviewCreateRequestDto request) {

        Member member = memberService.findUserFromToken();

        Space space = spaceRepository.findById(request.getSpaceId())
                                     .orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));

        reviewRepository.save(
                Review.builder()
                      .member(member)
                      .space(space)
                      .title(request.getTitle())
                      .content(request.getContent())
                      .grade(request.getGrade())
                      .build());
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() -> new NotFoundReviewException("일치하는 후기 정보가 존재하지 않습니다."));

        reviewRepository.delete(review);
    }


    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewList(Long spaceId) {
        Space space = spaceRepository.findById(spaceId)
                                     .orElseThrow(() -> new NotFoundSpaceException("공간 정보를 찾을 수 없습니다."));


        return space.getReviews().stream()
                    .map(ReviewDto::fromEntity)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void updateReview(ReviewRequestUpdateDto dto, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                                        .orElseThrow(() -> new NotFoundReviewException("일치하는 후기 정보가 존재하지 않습니다."));

        review.updateReview(dto.getTitle(), dto.getContent(), dto.getGrade());
    }
}
