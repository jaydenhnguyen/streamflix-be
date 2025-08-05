package com.example.steamflix_be.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final String appErrorCode;
    private final HttpStatus httpStatus;

    public AppException(String appErrorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.appErrorCode = appErrorCode;
        this.httpStatus = httpStatus;
    }
}
