package com.coupon.favorites.items.itemsprice.application.usecase;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.itemsprice.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.itemsprice.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import io.vavr.control.Either;

import java.util.List;


public class GetItemsPriceUseCaseImpl implements GetItemsPriceUseCase {
    private final GetItemsPriceService getItemsPriceService;
    public GetItemsPriceUseCaseImpl(GetItemsPriceService getItemsPriceService){
        this.getItemsPriceService = getItemsPriceService;
    }


    @Override
    public Either<ErrorCoupon, List<Item>> execute(ItemsId itemsId) {
        return getItemsPriceService.getItemsPrice(itemsId);
    }


}
