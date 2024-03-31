package com.coupon.favorites.items.maximizedcoupon.domain.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import io.vavr.control.Either;

import java.util.List;

public interface GetItemsPriceService {
    Either<ErrorCoupon, List<Item>> getItemsPrice(ItemsId itemsId);

}
