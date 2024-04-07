package com.coupon.favorites.items.coupon.domain.event;

public interface SaveCachePricesEvent {
    void savePricesInCache(SaveCachePricesValueEvent valueEvent);
}
