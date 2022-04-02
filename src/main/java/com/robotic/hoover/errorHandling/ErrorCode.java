package com.robotic.hoover.errorHandling;

public enum ErrorCode {

    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Internal Server Error"),
    INVALID_REQUEST("INVALID_REQUEST", "Invalid request body or parameters supplied"),
    INVALID_COORDINATE_VALUE("INVALID_COORDINATE_VALUE", "Invalid Coordinate values");

    private final String errorCode;
    private final String message;

    private ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return errorCode + ": " + message;
    }
}