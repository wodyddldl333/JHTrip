package com.ssafy.jhtrip.notice.controller;

import com.ssafy.jhtrip.exception.NoticeDetailException;
import com.ssafy.jhtrip.exception.NoticeRegistrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class NoticeExceptionHandler {
    @ExceptionHandler(NoticeRegistrationException.class)
    public ResponseEntity<String> handlerNoticeRegistrationException(NoticeRegistrationException ex) {
        log.error("Notice registration error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoticeDetailException.class)
    public ResponseEntity<String> handlerNoticeDetailException(NoticeDetailException ex) {
        log.error("Notice Detail error: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
