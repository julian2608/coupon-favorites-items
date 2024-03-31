package com.coupon.favorites.items.maximizedcoupon.domain.entity;

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
}
