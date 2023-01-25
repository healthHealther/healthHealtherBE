package com.health.healther.service;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.BoardRepository;
import com.health.healther.dto.board.BoardCreateRequestDto;
import com.health.healther.dto.board.BoardDetailResponseDto;
import com.health.healther.exception.board.NotFoundBoardException;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberService memberService;

    public void createBoard(BoardCreateRequestDto request) {

        Member member = memberService.findUserFromToken();

        boardRepository.save(Board.builder()
                                  .member(member)
                                  .title(request.getTitle())
                                  .content(request.getContent())
                                  .likeCount(0)
                                  .build());
    }

    public BoardDetailResponseDto getBoardDetail(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException("게시판 정보를 찾을 수 없습니다."));

        return BoardDetailResponseDto.of(board);
    }
}