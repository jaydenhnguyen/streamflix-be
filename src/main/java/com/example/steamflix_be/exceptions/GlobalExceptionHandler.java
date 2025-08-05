package com.example.steamflix_be.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(Map.of(
                        "statusCode", ex.getHttpStatus().value(),
                        "appErrorCode", ex.getAppErrorCode(),
                        "message", ex.getMessage()
                ));
    }
}
