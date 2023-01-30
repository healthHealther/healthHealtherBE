package com.health.healther.controller;


import com.health.healther.dto.board.*;
import com.health.healther.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @PutMapping("/{boardId}")
    public ResponseEntity updateBoard(
            @PathVariable("boardId") Long id,
            @RequestBody BoardUpdateRequestDto request
    ) {
        boardService.updateBoard(id, request);
        return ResponseEntity.ok().build();
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
    @GetMapping("/like/{boardId}")
    public ResponseEntity boardIsLiked(
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.boardIsLiked(id), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comment")
    public ResponseEntity<CommentRegisterResponseDto> registerComment(
            @RequestBody CommentRegisterRequestDto request,
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.registerComment(id, request), HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}/comment")
    public ResponseEntity<List<CommentListResponseDto>> getCommentList(
            @RequestBody @Valid CommentListRequestDto request,
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.getCommentList(id,request), HttpStatus.OK);
    }
}