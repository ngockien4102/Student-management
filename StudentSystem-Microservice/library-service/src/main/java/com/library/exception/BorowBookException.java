package com.library.exception;

import com.library.dto.Response.ExceptionResponse;

public class BorowBookException extends RuntimeException{
    public BorowBookException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getCode()+": "+ exceptionResponse.getMassage());
    }
}
