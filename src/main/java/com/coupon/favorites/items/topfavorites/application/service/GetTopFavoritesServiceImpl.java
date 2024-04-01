package com.coupon.favorites.items.topfavorites.application.service;

import com.coupon.favorites.items.topfavorites.domain.entity.ErrorFavorites;
import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;
import com.coupon.favorites.items.topfavorites.domain.service.GetTopFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTopFavoritesServiceImpl implements GetTopFavoritesService {

    private final ItemFavoriteRepository itemFavoriteRepository;

    public GetTopFavoritesServiceImpl(ItemFavoriteRepository itemFavoriteRepository) {
        this.itemFavoriteRepository = itemFavoriteRepository;
    }

    @Override
    public Either<ErrorFavorites, List<ItemFavorite>> execute(int maxTop) {
        List<ItemFavorite> itemFavorites = itemFavoriteRepository.getTopFavorites(maxTop);
        return !itemFavorites.isEmpty() ? Either.right(itemFavorites) : Either.left(ErrorFavorites.ErrorGettingFavorites);
    }
}
