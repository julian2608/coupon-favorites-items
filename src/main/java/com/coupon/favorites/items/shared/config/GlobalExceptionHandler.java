package com.coupon.favorites.items.shared.config;

import com.coupon.favorites.items.retrofit.application.domain.ApiCallException;
import com.coupon.favorites.items.shared.exception.ValidationDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiCallException.class)
    public ResponseEntity<String> handleException(ApiCallException ex) {
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
                .body(String.format("One of the calls to external apis failed with code: %s. Detail: %s", ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(ValidationDataException.class)
    public ResponseEntity<String> handleValidationDataException(ValidationDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
