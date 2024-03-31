package com.coupon.favorites.items.topfavorites.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorFavorites {
    private HttpStatus code;
    private String message;

    public static ErrorFavorites ErrorUpdateFavorites
            = ErrorFavorites
            .builder()
            .code(HttpStatus.FAILED_DEPENDENCY)
            .message("Error updating favorites.")
            .build();
}
