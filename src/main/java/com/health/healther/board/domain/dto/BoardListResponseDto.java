package com.health.healther.board.domain.dto;


import com.health.healther.board.domain.model.Board;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponseDto {

    private Long boardId;

    private String nickName;

    private String title;

    private int commentCount; // 게시판 댓글 갯수

    public static BoardListResponseDto from(Board board) {

        return BoardListResponseDto.builder()
                .boardId(board.getId())
                .nickName(board.getMember().getNickName())
                .title(board.getTitle())
                .commentCount(board.getComments().size())
                .build();
    }
}
