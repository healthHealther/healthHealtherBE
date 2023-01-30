package com.health.healther.dto.board;

import com.health.healther.domain.model.Board;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardListResponseDto {

    private Long boardId;

    private String nickName;

    private String title;

    private int commentCount;

    public static GetBoardListResponseDto from(Board board) {
        return GetBoardListResponseDto.builder()
                .boardId(board.getId())
                .nickName(board.getMember().getNickName())
                .title(board.getTitle())
                .commentCount(board.getComments().size())
                .build();
    }
}
