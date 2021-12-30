package com.example.spring.security.basic.dto.Request;

import com.example.spring.security.basic.exception.ResourceNotFoundException;

public class ErrorCodeRequest {
    private static final int notFound = 80911;
    private static final int duplicate = notFound + 1;

    public String getMessage(int code)  {
        switch (code) {
            case notFound:
                return " not found !!!";
            case duplicate:
                return " duplicate !!! ";
        }
        return "";
    }
}
