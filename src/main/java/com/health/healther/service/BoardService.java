package com.health.healther.service;

import com.health.healther.domain.model.Board;
import com.health.healther.domain.model.Member;
import com.health.healther.domain.repository.BoardRepository;
import com.health.healther.dto.board.BoardCreateRequestDto;
import lombok.RequiredArgsConstructor;
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
}
