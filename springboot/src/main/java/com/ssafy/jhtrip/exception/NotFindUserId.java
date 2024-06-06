package com.ssafy.jhtrip.exception;

public class NotFindUserId extends RuntimeException {
    public NotFindUserId(String message) {
        super(message);
    }
}
