package com.student.exception;

import com.student.dto.Response.ExceptionResponse;

public class DuplicateRollnumberException extends RuntimeException{
    public DuplicateRollnumberException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getMessage());
    }
}
