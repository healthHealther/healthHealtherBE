package com.health.healther.dto.board;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequestDto {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    private String content;
}
