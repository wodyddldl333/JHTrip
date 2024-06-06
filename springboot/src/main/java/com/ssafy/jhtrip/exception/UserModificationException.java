package com.ssafy.jhtrip.exception;

public class UserModificationException extends RuntimeException {
	public UserModificationException(String message) {
        super(message);
    }
	
	public UserModificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
