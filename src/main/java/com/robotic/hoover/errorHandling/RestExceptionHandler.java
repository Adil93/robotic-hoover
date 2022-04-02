package com.robotic.hoover.errorHandling;

import com.robotic.hoover.errorHandling.exception.AppException;
import com.robotic.hoover.errorHandling.exception.InternalServerError;
import com.robotic.hoover.errorHandling.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ApiError> appException(AppException ex) {
        ApiError apiError = new ApiError(ex.getMessage(), ex.getStatus().value(), ex.getErrorCode());
        log.error(apiError.toString());
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidRequest(final Exception ex) {
        log.error(ex.getClass().getName(), ex.getMessage());
        AppException exception = new ValidationException(ErrorCode.INVALID_REQUEST);
        ApiError apiError = new ApiError(exception.getMessage(), exception.getStatus().value(), exception.getErrorCode());
        return new ResponseEntity<>(apiError, exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidArgumentsException(MethodArgumentNotValidException exception) {
        final List<ErrorDetail> errorDetails = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorDetail(((FieldError) error).getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        final ApiError apiError = new ApiError(ErrorCode.INVALID_REQUEST.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.INVALID_REQUEST.getErrorCode(),
                errorDetails);
        log.error(apiError.toString());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleAll(final Exception ex) {
        log.error(ex.getClass().getName(), ex);
        AppException exception = new InternalServerError();
        ApiError apiError = new ApiError(exception.getMessage(), exception.getStatus().value(), exception.getErrorCode());
        return new ResponseEntity<ApiError>(apiError, exception.getStatus());
    }
}