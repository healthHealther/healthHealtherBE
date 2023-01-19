package com.health.healther.service;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.dto.BoardListResponseDto;
import com.health.healther.board.domain.model.Board;
import com.health.healther.board.domain.repository.BoardRepository;
import com.health.healther.domain.model.Member;
import com.health.healther.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberService memberService;



    @Transactional
    public void createBoard(BoardCreateRequestDto request) {

        Member member = memberService.findUserFromToken();

        boardRepository.save(Board.builder()
                              .member(member)
                              .title(request.getTitle())
                              .content(request.getContent())
                              .likeCount(0)
                              .build()
        );
    }

    public List<BoardListResponseDto> getBoardList(Pageable pageable) {

        int page = (Pageable.unpaged().getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        int size = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "board_id"));

        return boardRepository.findAll(pageRequest)
                .stream()
                .map(BoardListResponseDto :: from)
                .collect(Collectors.toList());
    }
}
