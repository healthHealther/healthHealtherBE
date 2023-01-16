package com.health.healther.board.domain.dto;


import com.health.healther.board.domain.model.Board;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class QueryBoardResponseDto { // 게시판 조회 응답 Dto

    private Long boardId;

    private String nickName;

    private String title;

    private String content;

    public static QueryBoardResponseDto fromEntity(Board board) {

        return QueryBoardResponseDto.builder()
                .boardId(board.getId())
                .nickName(board.getMember().getNickName())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
