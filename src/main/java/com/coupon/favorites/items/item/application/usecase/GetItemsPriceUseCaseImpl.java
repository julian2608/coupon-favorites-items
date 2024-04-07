package com.coupon.favorites.items.item.application.usecase;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.item.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.item.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import io.vavr.control.Either;

import java.util.List;
import java.util.Set;


public class GetItemsPriceUseCaseImpl implements GetItemsPriceUseCase {
    private final GetItemsPriceService getItemsPriceService;
    public GetItemsPriceUseCaseImpl(GetItemsPriceService getItemsPriceService){
        this.getItemsPriceService = getItemsPriceService;
    }


    @Override
    public Either<ErrorCoupon, List<Item>> execute(Set<String> itemsId) {
        return getItemsPriceService.getItemsPrice(itemsId);
    }


}
