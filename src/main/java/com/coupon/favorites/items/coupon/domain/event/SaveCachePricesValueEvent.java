package com.coupon.favorites.items.coupon.domain.event;

import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SaveCachePricesValueEvent {
    private List<Item> items;
}
