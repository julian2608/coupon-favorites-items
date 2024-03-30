package com.coupon.favorites.items.shared.domain;

public class ApiCallException  extends RuntimeException {

    private final int code;
    private final String message;

    public ApiCallException(final int code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}