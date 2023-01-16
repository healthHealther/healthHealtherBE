package com.health.healther.board.service.impl;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.model.Board;
import com.health.healther.board.domain.repository.BoardRepository;
import com.health.healther.board.service.BoardService;
import com.health.healther.domain.model.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void createBoard(BoardCreateRequestDto request) {

        Member member = memberService.findUserFromToken();

        boardRepository.save(Board.builder()
                              .member(member)
                              .title(request.getTitle())
                              .content(request.getContent())
                              .build()
        );
    }
}
