package com.ssafy.jhtrip.exception;

public class BoardRegistrationException extends RuntimeException {
    public BoardRegistrationException(String message) {
        super(message);
    }

    public BoardRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
