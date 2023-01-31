package com.health.healther.dto.review;


import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequestDto {

    @NotNull
    private Long spaceId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Integer grade;
}
