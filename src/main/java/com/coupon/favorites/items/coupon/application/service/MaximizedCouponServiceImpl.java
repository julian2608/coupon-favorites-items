package com.coupon.favorites.items.coupon.application.service;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.coupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.coupon.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.coupon.domain.valueobject.ItemsId;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class MaximizedCouponServiceImpl implements MaximizeCouponService {

    private final GetItemsPriceUseCase getItemsPriceUseCase;

    public MaximizedCouponServiceImpl(
            GetItemsPriceUseCase getItemsPriceUseCase) {
        this.getItemsPriceUseCase = getItemsPriceUseCase;
    }

    @Override
    public Either<ErrorCoupon, MaximizeCouponResponse> maximizeCoupon(MaximizeCouponEntity couponRequest){
        List<Item> itemsPrice = getItemsPrice(couponRequest.getFavoritesItems());

        double amountCoupon = couponRequest.getCoupon().getValue();

        MaximizeCouponResponse resultSum = sumSequence(itemsPrice, amountCoupon);


        resultSum = binaryPriceSearch(itemsPrice, resultSum, amountCoupon);


        return Either.right(resultSum);
    }

    private MaximizeCouponResponse sumSequence(List<Item> itemsPrice, double amountCoupon) {
        double resultSum = 0.0;
        List<Item> itemsInSum = new ArrayList<>();
        Item lastItem = new Item();

        Collections.sort(itemsPrice, Comparator.comparing(Item::getPrice));

        Iterator<Item> iterator = itemsPrice.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            Item item = iterator.next();
            if (resultSum < amountCoupon) {
                resultSum += item.getPrice();
                itemsInSum.add(item);
                iterator.remove();
            }
            else {
                lastItem = item;
                break;
            }
        }

        itemsPrice.removeAll(itemsInSum);

        if (resultSum > amountCoupon){
            resultSum -= lastItem.getPrice();
            itemsInSum.remove(lastItem);
        }

        return builderMaximizeCouponResponse(itemsInSum, resultSum);
    }

    private MaximizeCouponResponse builderMaximizeCouponResponse(List<Item> itemsInSum, double resultSum) {
        return MaximizeCouponResponse
                .builder()
                .itemIds(itemsInSum.stream().map(item -> item.getId()).collect(Collectors.toList()))
                .total(resultSum)
                .build();
    }

    private MaximizeCouponResponse binaryPriceSearch(List<Item> itemList, MaximizeCouponResponse resultSum, double amountCoupon) {
        int low = 0;
        int high = itemList.size() - 1;
        int result = -1;
        double priceToFind = amountCoupon - resultSum.getTotal();

        while (low <= high) {
            int mid = low + (high - low) / 2;
            double price = itemList.get(mid).getPrice();
            if (price <= (priceToFind - priceToFind * 0.10)
            || price <= priceToFind ) {
                result = mid;
                break;
            } else if (price < priceToFind) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if (result != -1) {
            Item item = itemList.get(result);
            resultSum.getItemIds().add(item.getId());
            resultSum.setTotal(resultSum.getTotal() + item.getPrice());
        }

        return resultSum;
    }

    private List<Item> getItemsPrice(ItemsId itemsId){
        return getItemsPriceUseCase.execute(itemsId).fold(
                error -> {
                    throw new RuntimeException(
                            String.format("Detail: %s. Code status: %s",error.getMessage(), error.getCode().value()));
                },
                result -> result
        );
    }

}
