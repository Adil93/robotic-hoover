package com.robotic.hoover.errorHandling.exception;

import com.robotic.hoover.errorHandling.ErrorCode;
import org.springframework.http.HttpStatus;

public class InternalServerError extends AppException {

    public InternalServerError() {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_SERVER_ERROR.getErrorCode());
    }
}
