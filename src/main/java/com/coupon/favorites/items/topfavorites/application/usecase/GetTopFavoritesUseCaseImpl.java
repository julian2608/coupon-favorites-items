package com.coupon.favorites.items.topfavorites.application.usecase;

import com.coupon.favorites.items.topfavorites.domain.entity.ErrorFavorites;
import com.coupon.favorites.items.topfavorites.domain.entity.ItemFavorite;
import com.coupon.favorites.items.topfavorites.domain.service.GetTopFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import com.coupon.favorites.items.topfavorites.domain.usecase.GetTopFavoritesUseCase;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;

public class GetTopFavoritesUseCaseImpl implements GetTopFavoritesUseCase {

    private final GetTopFavoritesService getTopFavoritesService;

    public GetTopFavoritesUseCaseImpl(GetTopFavoritesService getTopFavoritesService) {
        this.getTopFavoritesService = getTopFavoritesService;
    }

    @Override
    public Either<ErrorFavorites, List<ItemFavorite>> execute(int maxTop) {
        return getTopFavoritesService.execute(maxTop);
    }
}
