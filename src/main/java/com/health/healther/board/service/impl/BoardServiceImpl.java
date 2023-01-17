package com.health.healther.board.service.impl;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.dto.QueryBoardResponseDto;
import com.health.healther.board.domain.model.Board;
import com.health.healther.board.domain.repository.BoardRepository;
import com.health.healther.board.exception.NoFoundBoardException;
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

//        Member member = memberService.findUserFromToken();

        Member member = new Member();

        boardRepository.save(Board.builder()
                              .member(member)
                              .title(request.getTitle())
                              .content(request.getContent())
                              .likeCount(0)
                              .build()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public QueryBoardResponseDto getBoard(long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoFoundBoardException("일치하는 게시글 정보가 존재하지 않습니다."));

        return QueryBoardResponseDto.fromEntity(board);

    }
}
