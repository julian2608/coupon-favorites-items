package com.coupon.favorites.items.shared;

import lombok.Getter;

public class ApiCallException  extends RuntimeException {

    @Getter
    private final int code;
    private final String message;

    public ApiCallException(final int code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}