package com.coupon.favorites.items.itemsprice.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import io.vavr.control.Either;

import java.util.List;
import java.util.Set;

public interface GetItemsPriceService {
    Either<ErrorCoupon, List<Item>> getItemsPrice(Set<String> itemsId);

}
