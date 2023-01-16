package com.health.healther.board.service;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;

public interface BoardService {
    void createBoard(BoardCreateRequestDto request);
}
