package com.health.healther.controller;


import com.health.healther.dto.board.BoardCreateRequestDto;
import com.health.healther.dto.board.CommentRegisterDto.RequestDto;
import com.health.healther.dto.board.CommentRegisterDto.ResponseDto;
import com.health.healther.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/board")
@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity createBoard(
            @RequestBody @Valid BoardCreateRequestDto request) {

        boardService.createBoard(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity getBoardDetail(
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.getBoardDetail(id), HttpStatus.OK);
    }
    
    @DeleteMapping("{boardId}")
    public ResponseEntity deleteBoard(
            @PathVariable("boardId") Long id
    ) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping("/like/{boardId}")
    public ResponseEntity likeBoard(
            @PathVariable("boardId") Long id
    ) {
        boardService.likeBoard(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/like/{boardId}")
    public ResponseEntity deleteBoardLike(
            @PathVariable("boardId") Long id
    ) {
        boardService.deleteBoardLike(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{boardId}/comment")
    public ResponseEntity<ResponseDto> registerComment(
            @RequestBody RequestDto request,
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(
                boardService.registerComment(id,request), HttpStatus.OK
        );
    }
}