package com.health.healther.board.domain.dto;

import com.health.healther.board.domain.model.Board;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponse { // 게시글 상세 조회 응답 dto

    private Long boardId;

    private String nickName;

    private String title;

    private String content;

    public static BoardDetailResponse from(Board board) {

        return BoardDetailResponse.builder()
                .boardId(board.getId())
                .nickName(board.getMember().getNickName())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
