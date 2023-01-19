package com.health.healther.dto.review;

import com.health.healther.domain.model.Review;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private String spaceName;

    private String MemberName;

    private String nickName;

    private String title;

    private String content;

    private int grade;

    public static ReviewDto fromEntity(Review review) {
        return ReviewDto.builder()
                        .spaceName(review.getSpace().getTitle())
                        .MemberName(review.getMember().getName())
                        .nickName(review.getMember().getNickName())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .grade(review.getGrade())
                        .build();
    }
}