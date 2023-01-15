package com.health.healther.review.domain.dto;


import com.health.healther.domain.model.Member;
import com.health.healther.review.domain.model.Review;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Member member;

    private String content;

    private int grade;

    public static ReviewDto fromEntity(Review review){

        return ReviewDto.builder()
                .member(review.getMember())
                .content(review.getContent())
                .grade(review.getGrade())
                .build();
    }
}
