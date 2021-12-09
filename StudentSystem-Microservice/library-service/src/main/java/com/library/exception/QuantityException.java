package com.library.exception;

import com.library.dto.Response.ExceptionResponse;

public class QuantityException extends RuntimeException{
    public QuantityException(ExceptionResponse exceptionDto) {
        super (exceptionDto.getCode()+": "+exceptionDto.getMassage());
    }
}
