package com.health.healther.board.controller;

import com.health.healther.board.domain.dto.BoardCreateRequestDto;
import com.health.healther.board.domain.dto.QueryBoardResponseDto;
import com.health.healther.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{boardId}")
    public ResponseEntity<QueryBoardResponseDto> getBoard(
            @PathVariable("boardId") long boardId ) {

        return new ResponseEntity<>(
                QueryBoardResponseDto.fromEntity(boardService.getBoard(boardId)),
                HttpStatus.OK
        );
    }
}
