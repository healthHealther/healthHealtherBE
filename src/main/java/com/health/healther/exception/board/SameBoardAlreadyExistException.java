package com.health.healther.exception.board;

public class SameBoardAlreadyExistException extends RuntimeException {
    public SameBoardAlreadyExistException(String message) {
        super(message);
    }
}
