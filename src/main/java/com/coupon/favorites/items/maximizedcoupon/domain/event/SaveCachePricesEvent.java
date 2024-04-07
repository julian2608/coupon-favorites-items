package com.coupon.favorites.items.maximizedcoupon.domain.event;

public interface SaveCachePricesEvent {
    void savePricesInCache(SaveCachePricesValueEvent valueEvent);
}
