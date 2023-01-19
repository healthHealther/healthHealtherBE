package com.health.healther.board.controller;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.dto.QueryBoardResponseDto;
import com.health.healther.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
@RestController
public class BoardController {

    private final BoardService boardService;
    @PostMapping
    public ResponseEntity<Object> createBoard(
            @RequestBody @Valid BoardCreateRequestDto request) {

        boardService.createBoard(request);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<QueryBoardResponseDto>> getBoardList(
            final Pageable pageable
    ) {

        PageRequest pageRequest
                = PageRequest.of(
                        pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "board_id")
        );

        return new ResponseEntity<>(
                boardService.getBoardList(pageRequest), HttpStatus.OK
        );
    }

}
