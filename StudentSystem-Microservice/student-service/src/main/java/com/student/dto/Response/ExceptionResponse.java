package com.student.dto.Response;

import com.student.exception.ErrorCode;

public class ExceptionResponse {
    private int errorCode;
    private String message;

    public ExceptionResponse() {
    }

    public ExceptionResponse(int errorCode) {
        this.errorCode = errorCode;
        this.message = ErrorCode.getErrorMessage(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
