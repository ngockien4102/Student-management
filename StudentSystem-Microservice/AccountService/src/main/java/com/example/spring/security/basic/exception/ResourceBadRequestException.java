package com.example.spring.security.basic.exception;

import com.example.spring.security.basic.dto.Request.ErrorCodeRequest;
import com.example.spring.security.basic.dto.Response.ExceptionResponse;

public class ResourceBadRequestException extends RuntimeException{
    public ResourceBadRequestException(ExceptionResponse exceptionResponse) {
        super(exceptionResponse.getCode()+": "+exceptionResponse.getMesage());
    }
}
