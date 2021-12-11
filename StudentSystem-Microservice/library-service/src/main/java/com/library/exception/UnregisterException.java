package com.library.exception;

import com.library.dto.Response.ExceptionResponse;

public class UnregisterException extends RuntimeException{
    public UnregisterException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getCode()+": "+exceptionResponse.getMassage());
    }
}
