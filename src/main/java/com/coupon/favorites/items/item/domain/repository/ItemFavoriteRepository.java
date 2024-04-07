package com.coupon.favorites.items.item.domain.repository;

import com.coupon.favorites.items.item.domain.entity.ItemFavorite;

import java.util.Collection;
import java.util.List;


public interface ItemFavoriteRepository {
    void incrementQuantity(Collection<String> itemFavorite);

    List<ItemFavorite> getTopFavorites(int maxTop);

}
