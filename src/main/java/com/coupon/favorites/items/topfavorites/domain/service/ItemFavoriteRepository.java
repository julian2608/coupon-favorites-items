package com.coupon.favorites.items.topfavorites.domain.service;

import com.coupon.favorites.items.topfavorites.domain.entity.ErrorFavorites;
import io.vavr.control.Either;


public interface ItemFavoriteRepository {
    Integer incrementQuantityById(String itemFavorite);

}
