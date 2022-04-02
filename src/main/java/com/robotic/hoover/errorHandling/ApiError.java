package com.robotic.hoover.errorHandling;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Data
public class ApiError {

    private String errorId;
    private String errorCode = null;
    private int status;
    private String message;
    private List<ErrorDetail> errors;

    public ApiError(String message) {
        this.errorId = randomUUID().toString();
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ApiError(String message, int status) {
        this(message);
        this.status = status;
    }

    public ApiError(String message, int status, String errorCode) {
        this(message, status);
        this.errorCode = errorCode;
    }

    public ApiError(String message, int status, String errorCode, List<ErrorDetail> errors) {
        this(message, status, errorCode);
        this.errors = errors;
    }

    @Override
    public String toString() {
        if (StringUtils.hasText(errorCode)) {
            if (errors != null && errors.size() > 0) {
                return String.format("Error [ Id=%s, code=%s, message=%s, errors=%s]", errorId, errorCode, message, errors);
            } else {
                return String.format("Error [ Id=%s, code=%s, message=%s]", errorId, errorCode, message);
            }
        } else {
            return String.format("Error [ Id=%s, message=%s ]", errorId, message);
        }
    }
}