package com.health.healther.dto.board;

import com.health.healther.domain.model.Board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardSearchListResponseDto {
	private Long boardId;

	private String nickName;

	private String title;

	private String content;

	private int commentCount;

	public static BoardSearchListResponseDto from(Board board) {
		return BoardSearchListResponseDto.builder()
			.boardId(board.getId())
			.nickName(board.getMember().getNickName())
			.title(board.getTitle())
			.content(board.getContent())
			.commentCount(board.getComments().size())
			.build();
	}
}
