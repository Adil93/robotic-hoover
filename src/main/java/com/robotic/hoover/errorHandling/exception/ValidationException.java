package com.robotic.hoover.errorHandling.exception;

import com.robotic.hoover.errorHandling.ErrorCode;
import org.springframework.http.HttpStatus;

public class ValidationException extends AppException {

    public ValidationException(ErrorCode error) {
        super(error.getMessage(), HttpStatus.BAD_REQUEST, error.getErrorCode());
    }
}
