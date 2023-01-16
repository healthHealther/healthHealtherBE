package com.health.healther.review.domain.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestUpdateDto {

    @NotBlank
    private String content;

    @NotNull
    private int grade;
}
