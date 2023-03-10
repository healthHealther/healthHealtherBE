package com.health.healther.exception.board;

import com.health.healther.controller.BoardController;
import com.health.healther.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = BoardController.class)
public class BoardExceptionHandler {

    @ExceptionHandler(NotFoundBoardException.class)
    public ResponseEntity<ErrorMessage> NotFoundBoardException(
            NotFoundBoardException exception
    ) {
        return ResponseEntity.badRequest()
                             .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundBoardLikeException.class)
    public ResponseEntity<ErrorMessage> NotFoundBoardLikeException(
            NotFoundBoardLikeException exception
    ) {
        return ResponseEntity.badRequest()
                             .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AlreadyBoardLikeException.class)
    public ResponseEntity<ErrorMessage> AlreadyBoardLikeException(
            AlreadyBoardLikeException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AlreadyDeleteBoardLikeException.class)
    public ResponseEntity<ErrorMessage> AlreadyDeleteBoardLikeException(
            AlreadyDeleteBoardLikeException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundCommentException.class)
    public ResponseEntity<ErrorMessage> NotFoundCommentException(
            NotFoundCommentException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
