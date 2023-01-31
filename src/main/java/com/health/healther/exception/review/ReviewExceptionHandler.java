package com.health.healther.exception.review;


import com.health.healther.controller.ReviewController;
import com.health.healther.exception.ErrorMessage;
import com.health.healther.exception.space.NotFoundSpaceException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ReviewController.class)
public class ReviewExceptionHandler {

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ErrorMessage> NotFoundMemberException(
            NotFoundMemberException exception
    ) {
        return ResponseEntity.badRequest()
                         .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }


    @ExceptionHandler(NotFoundSpaceException.class)
    public ResponseEntity<ErrorMessage> NotFoundSpaceException(
            NotFoundSpaceException exception
    ) {
       return ResponseEntity.badRequest()
               .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundReviewException.class)
    public ResponseEntity<ErrorMessage> NoFoundReviewException(
            NotFoundReviewException exception
    ) {
        return ResponseEntity.badRequest()
                             .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AlreadyCreateReviewException.class)
    public ResponseEntity<ErrorMessage> AlreadyCreateReviewException(
            AlreadyCreateReviewException exception
    ) {
        return ResponseEntity.badRequest()
                             .body(ErrorMessage.of(exception, HttpStatus.BAD_REQUEST));
    }
}
