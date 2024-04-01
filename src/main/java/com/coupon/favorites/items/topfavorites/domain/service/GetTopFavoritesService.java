package com.coupon.favorites.items.topfavorites.domain.service;

import com.coupon.favorites.items.topfavorites.domain.entity.ErrorFavorites;
import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;
import io.vavr.control.Either;

import java.util.List;

public interface GetTopFavoritesService {
    Either<ErrorFavorites, List<ItemFavorite>> execute (int maxTop);
}
