package com.health.healther.exception.review;

public class NotFoundMemberException extends RuntimeException{
    public NotFoundMemberException(String message) {
        super(message);
    }
}
