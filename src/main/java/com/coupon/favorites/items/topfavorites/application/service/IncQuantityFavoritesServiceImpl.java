package com.coupon.favorites.items.topfavorites.application.service;

import com.coupon.favorites.items.topfavorites.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.*;

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
            itemFavoriteRepository.incrementQuantity(favoritesItems);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
