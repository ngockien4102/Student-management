package com.library.exception;

import com.library.dto.Response.ExceptionResponse;

public class RegisterBookException extends RuntimeException{
    public RegisterBookException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getCode()+": "+exceptionResponse.getMassage());
    }
}
