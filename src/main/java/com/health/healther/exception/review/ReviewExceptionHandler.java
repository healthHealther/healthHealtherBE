package com.health.healther.exception.review;


import com.health.healther.exception.review.NoFoundMemberException;
import com.health.healther.exception.review.NoFoundReviewException;
import com.health.healther.exception.review.NoFoundSpaceException;
import com.health.healther.review.exception.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(NoFoundMemberException.class)
    public ResponseEntity<ErrorMessage> NoSpaceExceptionHandler(
            NoFoundMemberException exception
    ) {
        return ResponseEntity.badRequest()
                         .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(NoFoundSpaceException.class)
    public ResponseEntity<ErrorMessage> NoMemberExceptionHandler(
            NoFoundSpaceException exception
    ) {
       return ResponseEntity.badRequest()
               .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NoFoundReviewException.class)
    public ResponseEntity<ErrorMessage> NoFoundReviewExceptionHandler(
            NoFoundReviewException exception
    ) {
        return ResponseEntity.badRequest()
                             .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
