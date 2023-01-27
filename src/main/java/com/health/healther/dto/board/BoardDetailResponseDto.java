package com.health.healther.dto.board;

import com.health.healther.domain.model.Board;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailResponseDto {

    private Long boardId;

    private String nickName;

    private String title;

    private String content;

    private int commentCount;

    public static BoardDetailResponseDto of(Board board) {

        return BoardDetailResponseDto.builder()
                .boardId(board.getId())
                .nickName(board.getMember().getNickName())
                .title(board.getTitle())
                .content(board.getContent())
                .commentCount(board.getComments().size())
                .build();
    }
}
