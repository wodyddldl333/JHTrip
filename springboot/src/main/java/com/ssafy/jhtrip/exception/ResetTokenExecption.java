package com.ssafy.jhtrip.exception;

public class ResetTokenExecption extends RuntimeException {
    public ResetTokenExecption(String message) {
        super(message);
    }

    public ResetTokenExecption(String message, Throwable cause) {
        super(message, cause);
    }
}
