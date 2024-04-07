package com.coupon.favorites.items.item.domain.usecase;

import com.coupon.favorites.items.item.domain.entity.ErrorItem;
import com.coupon.favorites.items.item.domain.entity.ItemFavorite;
import io.vavr.control.Either;

import java.util.List;

public interface GetTopFavoritesUseCase {
    Either<ErrorItem, List<ItemFavorite>> execute (int maxTop);
}
