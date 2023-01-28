package com.health.healther.exception.board;

public class BoardLikeAlreadyExistException extends RuntimeException{
    public BoardLikeAlreadyExistException(String message) {
        super(message);
    }
}
