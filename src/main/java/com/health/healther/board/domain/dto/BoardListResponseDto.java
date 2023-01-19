package com.health.healther.board.domain.dto;


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

    private String tite;

    private int commendCnt;


}
