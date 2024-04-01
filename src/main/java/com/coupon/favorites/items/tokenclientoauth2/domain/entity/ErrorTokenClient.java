package com.coupon.favorites.items.tokenclientoauth2.domain.entity;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
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
