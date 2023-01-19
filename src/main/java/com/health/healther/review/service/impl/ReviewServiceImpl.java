package com.health.healther.review.service.impl;

import com.health.healther.domain.model.Member;
import com.health.healther.domain.model.Space;
import com.health.healther.domain.repository.MemberRepository;
import com.health.healther.domain.repository.SpaceRepository;
import com.health.healther.review.domain.dto.ReviewCreateRequestDto;
import com.health.healther.review.domain.model.Review;
import com.health.healther.review.domain.repository.ReviewRepository;
import com.health.healther.review.exception.review.NoFoundMemberException;
import com.health.healther.review.exception.review.NoFoundSpaceException;
import com.health.healther.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    private final SpaceRepository spaceRepository;

    private final MemberRepository memberRepository;

    @Override
    public void createReview(ReviewCreateRequestDto request) {

        Space space = spaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new NoFoundSpaceException("일치하는 공간 정보가 존재하지 않습니다.")
        );

        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new NoFoundMemberException("일치하는 회원 정보가 존재하지 않습니다.")
        );

        reviewRepository.save(Review.builder()
                .member(member)
                .space(space)
                .title(request.getTitle())
                .content(request.getContent())
                .grade(request.getGrade())
                .build());

    }

}