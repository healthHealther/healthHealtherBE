package com.health.healther.dto.review;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDto {

    @NotBlank
    private Long spaceId;

    @NotBlank
    private String title;

    @NotBlank
    @Length(min = 1, max = 500)
    private String content;

    @NotBlank
    private int grade;
}
