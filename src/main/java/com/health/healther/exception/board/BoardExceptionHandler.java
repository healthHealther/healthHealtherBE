package com.health.healther.exception.board;

import com.health.healther.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(value = NoFoundBoardException.class)
    public ResponseEntity<ErrorMessage> NoFoundBoardException(
            NoFoundBoardException exception
    ) {
        return ResponseEntity.badRequest()
                .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
