package com.coupon.favorites.items.itemsprice.domain.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import io.vavr.control.Either;
import java.util.List;
import java.util.Set;

public interface GetItemsPriceUseCase {
    Either<ErrorCoupon, List<Item>> execute(Set<String> itemsId);
}
