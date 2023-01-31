package com.health.healther.controller;

import java.util.List;

import javax.validation.Valid;

import com.health.healther.dto.board.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.healther.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @GetMapping
    public ResponseEntity<List<GetBoardListResponseDto>> getBoardList(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        return new ResponseEntity<>(boardService.getBoardList(page,size), HttpStatus.OK);
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
            @RequestBody @Valid BoardUpdateRequestDto request
    ) {
        boardService.updateBoard(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{boardId}")
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

    @GetMapping("/{boardId}/likeCount")
    public ResponseEntity<GetBoardLikeCountResponseDto> getLikeCount(
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.getLikeCount(id), HttpStatus.OK);
    }

    @GetMapping("/like/{boardId}")
    public ResponseEntity boardIsLiked(
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.boardIsLiked(id), HttpStatus.OK);
    }

    @PostMapping("/{boardId}/comment")
    public ResponseEntity<CommentRegisterResponseDto> registerComment(
            @RequestBody @Valid CommentRegisterRequestDto request,
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.registerComment(id, request), HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}/comment")
    public ResponseEntity<List<CommentListResponseDto>> getCommentList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @PathVariable("boardId") Long id
    ) {
        return new ResponseEntity<>(boardService.getCommentList(id,page,size), HttpStatus.OK);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity updateComment(
            @RequestBody @Valid CommentUpdateRequestDto request,
            @PathVariable("commentId") Long id
    ) {
        boardService.updateComment(id,request);
        return ResponseEntity.ok().build();
    }


    
   	@GetMapping("/search")
    public ResponseEntity<List<BoardSearchListResponseDto>> searchBoardTitle(
      @Valid BoardSearchListRequestDto request
    ) {
      return new ResponseEntity<>(boardService.searchBoardTitle(request), HttpStatus.OK);
    }
}