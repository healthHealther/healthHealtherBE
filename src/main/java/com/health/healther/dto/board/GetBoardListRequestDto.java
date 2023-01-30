package com.health.healther.dto.board;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardListRequestDto {

    @NotBlank
    private int page;

    @NotBlank
    private int size;
}
