package com.health.healther.board.domain.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateRequestDto {

    @Size(max = 50)
    @NotBlank
    private String title;


    @Size(max = 500)
    @NotBlank
    private String content;

}
