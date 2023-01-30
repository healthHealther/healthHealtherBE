package com.health.healther.dto.board;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentListRequestDto {

    @NotNull
    private int page;

    @NotNull
    private int size;
}
