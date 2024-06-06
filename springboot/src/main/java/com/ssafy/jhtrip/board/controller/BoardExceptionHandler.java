package com.ssafy.jhtrip.board.controller;

import com.ssafy.jhtrip.exception.BoardDetailException;
import com.ssafy.jhtrip.exception.BoardRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BoardExceptionHandler {

    @ExceptionHandler(BoardRegistrationException.class)
    public ResponseEntity<String> handBoardRegistrationException(BoardRegistrationException ex) {
        log.error("Board registration error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BoardDetailException.class)
    public ResponseEntity<String> handBoardDetailException(BoardDetailException ex) {
        log.error("Board Detail error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
