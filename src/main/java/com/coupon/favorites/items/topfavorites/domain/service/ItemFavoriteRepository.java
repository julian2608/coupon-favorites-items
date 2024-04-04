package com.coupon.favorites.items.topfavorites.domain.service;

import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;

import java.util.Collection;
import java.util.List;


public interface ItemFavoriteRepository {
    void incrementQuantity(Collection<String> itemFavorite);

    List<ItemFavorite> getTopFavorites(int maxTop);

}
