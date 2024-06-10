package com.ssafy.jhtrip.user.controller;

import com.ssafy.jhtrip.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class UserExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<String> handleUserRegistrationException(UserRegistrationException ex) {
        log.error("User registration error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserModificationException.class)
    public ResponseEntity<String> handleUserModificationException(UserModificationException ex) {
        log.error("User modification error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFindUserId.class)
    public ResponseEntity<String> handleNotFindUserId(NotFindUserId ex) {
        log.error("User modification error: {}", ex.getMessage());
        return new ResponseEntity<>("Not Found User with email", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResetTokenException.class)
    public ResponseEntity<String> handleResetTokenExecption(ResetTokenException ex) {
        log.error("Reset token execption error: {}", ex.getMessage());
        return new ResponseEntity<>("Failed to save token", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotTokenValidation.class)
    public ResponseEntity<String> handleNotTokenValidation(NotTokenValidation ex) {
        log.error("Not token validation error: {}", ex.getMessage());
        return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> handleLoginException(LoginException ex) {
        log.error("Login error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
