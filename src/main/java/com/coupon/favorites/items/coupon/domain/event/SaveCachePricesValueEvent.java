package com.coupon.favorites.items.coupon.domain.event;

import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class SaveCachePricesValueEvent {
    private List<Item> items;
}
