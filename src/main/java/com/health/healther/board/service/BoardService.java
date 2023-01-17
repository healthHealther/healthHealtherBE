package com.health.healther.board.service;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.dto.QueryBoardResponseDto;

public interface BoardService {
    void createBoard(BoardCreateRequestDto request);

    QueryBoardResponseDto getBoard(long boardId);
}
