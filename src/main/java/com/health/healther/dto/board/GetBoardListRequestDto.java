package com.health.healther.dto.board;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardListRequestDto {

    @NotNull
    private int page;

    @NotNull
    private int size;
}
