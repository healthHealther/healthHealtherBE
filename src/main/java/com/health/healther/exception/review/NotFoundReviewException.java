package com.health.healther.exception.review;


public class NotFoundReviewException extends RuntimeException {
    public NotFoundReviewException(String message) {
        super(message);
    }
}