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
}
