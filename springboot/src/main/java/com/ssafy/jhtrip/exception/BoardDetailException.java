package com.ssafy.jhtrip.exception;

public class BoardDetailException extends RuntimeException {
    public BoardDetailException(String message) {
        super(message);
    }

    public BoardDetailException(String message, Throwable cause) {
        super(message, cause);
    }
}
