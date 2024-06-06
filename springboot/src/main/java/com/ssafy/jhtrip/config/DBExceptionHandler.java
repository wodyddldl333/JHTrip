package com.ssafy.jhtrip.config;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class DBExceptionHandler {

/*    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException ex) {
        log.error("SQL Exception: {}", ex.getMessage());
        return new ResponseEntity<>("Database access error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<String> handleDataAccessResourceFailureException(DataAccessResourceFailureException ex) {
        log.error("Data access resource failure: {}", ex.getMessage());
        return new ResponseEntity<>("Database connection failure", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<String> handleBadSqlGrammarException(BadSqlGrammarException ex) {
        log.error("Bad SQL grammar: {}", ex.getMessage());
        return new ResponseEntity<>("SQL syntax error", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    public ResponseEntity<String> handleUncategorizedSQLException(UncategorizedSQLException ex) {
        log.error("Uncategorized SQL exception: {}", ex.getMessage());
        return new ResponseEntity<>("General SQL error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Data integrity violation: {}", ex.getMessage());
        return new ResponseEntity<>("Data integrity violation", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.error("Empty result data access: {}", ex.getMessage());
        return new ResponseEntity<>("No data found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TransientDataAccessException.class)
    public ResponseEntity<String> handleTransientDataAccessException(TransientDataAccessException ex) {
        log.error("Transient data access exception: {}", ex.getMessage());
        return new ResponseEntity<>("Temporary database access issue", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(NonTransientDataAccessException.class)
    public ResponseEntity<String> handleNonTransientDataAccessException(NonTransientDataAccessException ex) {
        log.error("Non-transient data access exception: {}", ex.getMessage());
        return new ResponseEntity<>("Permanent database access issue", HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    // DB 무결성 예외
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        log.error("SQLIntegrityConstraintViolationException: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
