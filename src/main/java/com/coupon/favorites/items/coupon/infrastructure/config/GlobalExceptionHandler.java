package com.coupon.favorites.items.coupon.infrastructure.config;

import com.coupon.favorites.items.shared.domain.ApiCallException;
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
}
