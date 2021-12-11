package com.library.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(BadRequestException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage() ,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleForbiddenException(ForbiddenException ex, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(QuantityException.class)
    public ResponseEntity<Object> handleQuantityException(QuantityException ex, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<Object> handleDuplicateException(DuplicateException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RegisterBookException.class)
    public ResponseEntity<Object> handleRegisterBookException(RegisterBookException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BorowBookException.class)
    public ResponseEntity<Object> handleBorowBookException(BorowBookException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnregisterException.class)
    public ResponseEntity<Object> handleUnregisterException(UnregisterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

}
