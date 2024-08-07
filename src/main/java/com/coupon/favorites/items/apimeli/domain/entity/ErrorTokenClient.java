package com.coupon.favorites.items.apimeli.domain.entity;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
public class ErrorTokenClient {
    private HttpStatus code;
    private String message;

    public static ErrorCoupon ErrorGettingToken
            = ErrorCoupon
            .builder()
            .code(HttpStatus.FAILED_DEPENDENCY)
            .message("Error getting token api external.")
            .build();
}
