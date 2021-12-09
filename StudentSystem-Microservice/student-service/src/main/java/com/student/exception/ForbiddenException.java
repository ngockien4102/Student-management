package com.student.exception;

import com.student.dto.Response.ExceptionResponse;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getMessage());
    }
}
