package com.coupon.favorites.items.item.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorItem {
    private HttpStatus code;
    private String message;

    public static ErrorItem ErrorUpdateFavorites
            = ErrorItem
            .builder()
            .code(HttpStatus.FAILED_DEPENDENCY)
            .message("Error updating favorites.")
            .build();

    public static ErrorItem ErrorGettingFavorites
            = ErrorItem
            .builder()
            .code(HttpStatus.NOT_FOUND)
            .message("Favorite items not found.")
            .build();

    public static ErrorItem errorSerializerItem () {
        return ErrorItem
                .builder()
                .code(HttpStatus.BAD_REQUEST)
                .message("Error during serializer Item object.")
                .build();
    }
    public static ErrorItem errorDeserializerItem () {
        return ErrorItem
                .builder()
                .code(HttpStatus.BAD_REQUEST)
                .message("Error during deserializer Item object.")
                .build();
    }

}
