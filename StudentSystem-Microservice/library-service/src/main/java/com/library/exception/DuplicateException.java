package com.library.exception;

import com.library.dto.Response.ExceptionResponse;

public class DuplicateException extends RuntimeException{
    public DuplicateException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getCode()+": "+exceptionResponse.getMassage());
    }
}
