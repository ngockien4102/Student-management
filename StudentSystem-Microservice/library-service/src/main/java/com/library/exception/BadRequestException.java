package com.library.exception;

import com.library.dto.Response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(ExceptionResponse entity){
        super(entity.getCode()+": "+entity.getMassage());
    }

}
