package com.ssafy.jhtrip.exception;

public class ResetTokenException extends RuntimeException {
    public ResetTokenException(String message) {
        super(message);
    }

    public ResetTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
