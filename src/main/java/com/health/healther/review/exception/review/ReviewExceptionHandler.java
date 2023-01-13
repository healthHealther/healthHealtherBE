package com.health.healther.review.exception.review;


import com.health.healther.review.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(NoMemberException.class)
    public ResponseEntity<ErrorMessage> NoSpaceExceptionHandler(
            NoSpaceException exception
    ) {
        return ResponseEntity.badRequest()
                         .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(NoSpaceException.class)
    public ResponseEntity<ErrorMessage> NoMemberExceptionHandler(
            NoMemberException exception
    ) {
       return ResponseEntity.badRequest()
               .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
