package com.coupon.favorites.items.itemsprice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
@Builder
public class ErrorItemsPrice {
    private HttpStatus code;
    private String message;

    public static ErrorItemsPrice errorSerializerItem () {
        return ErrorItemsPrice
                .builder()
                .code(HttpStatus.BAD_REQUEST)
                .message("Error during serializer Item object.")
                .build();
    }
    public static ErrorItemsPrice errorDeserializerItem () {
        return ErrorItemsPrice
                .builder()
                .code(HttpStatus.BAD_REQUEST)
                .message("Error during deserializer Item object.")
                .build();
    }
}
