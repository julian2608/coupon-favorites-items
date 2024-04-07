package com.coupon.favorites.items.item.domain.entity;

import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ItemPriceResponse {
    private int code;

    private Item body;

}
