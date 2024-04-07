package com.coupon.favorites.items.item.application.service;

import com.coupon.favorites.items.item.domain.entity.ErrorItem;
import com.coupon.favorites.items.item.domain.entity.ItemFavorite;
import com.coupon.favorites.items.item.domain.service.GetTopFavoritesService;
import com.coupon.favorites.items.item.domain.repository.ItemFavoriteRepository;
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
    public Either<ErrorItem, List<ItemFavorite>> execute(int maxTop) {
        List<ItemFavorite> itemFavorites = itemFavoriteRepository.getTopFavorites(maxTop);
        return !itemFavorites.isEmpty() ? Either.right(itemFavorites) : Either.left(ErrorItem.ErrorGettingFavorites);
    }
}
