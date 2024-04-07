package com.coupon.favorites.items.item.domain.service;

import com.coupon.favorites.items.item.domain.entity.ErrorItem;
import com.coupon.favorites.items.item.domain.entity.ItemFavorite;
import io.vavr.control.Either;

import java.util.List;

public interface GetTopFavoritesService {
    Either<ErrorItem, List<ItemFavorite>> execute (int maxTop);
}
