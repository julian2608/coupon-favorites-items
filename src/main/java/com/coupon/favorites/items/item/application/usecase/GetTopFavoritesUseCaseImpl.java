package com.coupon.favorites.items.item.application.usecase;

import com.coupon.favorites.items.item.domain.entity.ErrorItem;
import com.coupon.favorites.items.item.domain.entity.ItemFavorite;
import com.coupon.favorites.items.item.domain.service.GetTopFavoritesService;
import com.coupon.favorites.items.item.domain.usecase.GetTopFavoritesUseCase;
import io.vavr.control.Either;
import java.util.List;

public class GetTopFavoritesUseCaseImpl implements GetTopFavoritesUseCase {

    private final GetTopFavoritesService getTopFavoritesService;

    public GetTopFavoritesUseCaseImpl(GetTopFavoritesService getTopFavoritesService) {
        this.getTopFavoritesService = getTopFavoritesService;
    }

    @Override
    public Either<ErrorItem, List<ItemFavorite>> execute(int maxTop) {
        return getTopFavoritesService.execute(maxTop);
    }
}
