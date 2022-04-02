package com.robotic.hoover.errorHandling.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

@Data
public class AppException extends Exception {
    private static final long serialVersionUID = 1L;

    private String errorCode = null;
    private HttpStatus status;
    private String message;

    public AppException(String message, HttpStatus status, String errorCode) {
        super(message);
        this.message = message;
        this.status = status;
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        if (StringUtils.hasText(errorCode)) {
            return errorCode + ": " + message;
        } else {
            return message;
        }
    }

}
