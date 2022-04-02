package com.robotic.hoover.errorHandling.exception;

import com.robotic.hoover.errorHandling.ErrorCode;
import org.springframework.http.HttpStatus;

public class UnProcessableEntityException extends AppException {

    public UnProcessableEntityException(ErrorCode error) {
        super(error.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, error.getErrorCode());
    }
}
