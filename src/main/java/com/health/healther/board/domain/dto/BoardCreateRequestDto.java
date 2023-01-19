package com.health.healther.board.domain.dto;

import com.health.healther.board.domain.model.Board;
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


    @Size(min = 1, max = 500)
    @NotBlank
    private String content;


    public static BoardCreateRequestDto from(Board board) {

        return BoardCreateRequestDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();

    }
}
