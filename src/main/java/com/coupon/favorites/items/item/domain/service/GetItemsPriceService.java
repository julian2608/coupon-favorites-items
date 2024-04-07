package com.coupon.favorites.items.item.domain.service;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import io.vavr.control.Either;

import java.util.List;
import java.util.Set;

public interface GetItemsPriceService {
    Either<ErrorCoupon, List<Item>> getItemsPrice(Set<String> itemsId);

}
