package com.coupon.favorites.items.coupon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
public class ErrorCoupon {
    private HttpStatus code;
    private String message;

    public static ErrorCoupon ErrorGettingPrices
            = ErrorCoupon
            .builder()
            .code(HttpStatus.FAILED_DEPENDENCY)
            .message("Error getting prices of items for maximize coupon.")
            .build();

    public static ErrorCoupon ErrorValueCouponDecimals
            = ErrorCoupon
            .builder()
            .code(HttpStatus.BAD_REQUEST)
            .message("The coupon amount can only have 2 decimals.")
            .build();

    public static ErrorCoupon ErrorValueCouponMin
            = ErrorCoupon
            .builder()
            .code(HttpStatus.BAD_REQUEST)
            .message("The coupon amount must be greater than 0.")
            .build();

    public static ErrorCoupon ErrorMaximizedCoupon(final String message) {
        return ErrorCoupon
                .builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .build();
    }

    public static ErrorCoupon ErrorEmptyList
            = ErrorCoupon
            .builder()
            .code(HttpStatus.BAD_REQUEST)
            .message("Empty list of items")
            .build();

}
