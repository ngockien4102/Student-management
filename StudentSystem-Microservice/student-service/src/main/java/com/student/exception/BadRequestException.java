package com.student.exception;

import com.student.dto.Response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException (ExceptionResponse exceptionResponse){
        super(exceptionResponse.getMessage());
    }
}
