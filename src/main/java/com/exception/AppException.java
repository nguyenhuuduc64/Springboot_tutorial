package com.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private final Object details;

    // Constructor 1: Chỉ errorCode
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());  // Lấy message từ ErrorCode
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.details = null;
    }
}