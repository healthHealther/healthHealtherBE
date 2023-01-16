package com.health.healther.board.controller;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
