package com.health.healther.review.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorMessage {

    private int code;
    private String errorSimpleName;
    private String msg;
    private LocalDateTime timestamp;

    public ErrorMessage(Exception exception, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.errorSimpleName = exception.getClass().getSimpleName();
        this.msg = exception.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
    }

    public static ErrorMessage of(Exception exception, HttpStatus httpStatus) {
        return new ErrorMessage(exception, httpStatus);
    }

}
