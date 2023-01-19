package com.health.healther.controller;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.dto.BoardDetailResponse;
import com.health.healther.board.domain.dto.BoardListResponseDto;
import com.health.healther.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<List<BoardListResponseDto>> getBoardList(
            Pageable pageable) {

        return new ResponseEntity<>(
                boardService.getBoardList(pageable), HttpStatus.OK
        );
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailResponse> getBoardDetail(
            @PathVariable("boardId") long boardId
    ) {

        return new ResponseEntity<>(
                boardService.getBoardDetail(boardId), HttpStatus.OK
        );
    }


}
