package com.health.healther.dto.board;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content; // @Max 필요할까?
}
