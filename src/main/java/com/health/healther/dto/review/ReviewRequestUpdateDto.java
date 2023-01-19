package com.health.healther.dto.review;

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
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private int grade;

}