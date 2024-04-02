package com.coupon.favorites.items.topfavorites.application.service;

import com.coupon.favorites.items.itemsprice.domain.entity.ItemPriceResponse;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.coupon.favorites.items.topfavorites.application.callable.IncQuantityFavoritesCallable;
import com.coupon.favorites.items.topfavorites.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class IncQuantityFavoritesServiceImpl implements IncQuantityFavoritesService {

    private final ItemFavoriteRepository itemFavoriteRepository;

    public IncQuantityFavoritesServiceImpl(
            ItemFavoriteRepository itemFavoriteRepository) {
        this.itemFavoriteRepository = itemFavoriteRepository;
    }

    @Override
    public void multiIncrementQuantity(Collection<String> favoritesItems) {
        try{
            Thread.ofVirtual().start(createTaskCallApi(favoritesItems));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private IncQuantityFavoritesCallable createTaskCallApi(Collection<String> favoritesItems) {
        return new IncQuantityFavoritesCallable(favoritesItems, itemFavoriteRepository);
    }

}
