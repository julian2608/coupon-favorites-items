package com.coupon.favorites.items.maximizedcoupon.domain.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import io.vavr.control.Either;
import java.util.List;

public interface GetItemsPriceUseCase {
    Either<ErrorCoupon, List<Item>> execute(ItemsId itemsId);
}
