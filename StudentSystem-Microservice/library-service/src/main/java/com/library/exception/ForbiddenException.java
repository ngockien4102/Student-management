package com.library.exception;

import com.library.dto.Response.ExceptionResponse;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(ExceptionResponse exceptionDto) {
        super(exceptionDto.getMassage()+": "+exceptionDto.getCode());
    }
}
