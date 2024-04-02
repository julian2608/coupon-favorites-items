package com.coupon.favorites.items.maximizedcoupon.application.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.maximizedcoupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.itemsprice.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import io.vavr.control.Either;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Comparator;
import java.util.stream.Collectors;
@Service
public class MaximizedCouponServiceImpl implements MaximizeCouponService {

    private final GetItemsPriceUseCase getItemsPriceUseCase;

    private final ApplicationEventPublisher publisher;

    public MaximizedCouponServiceImpl(
            GetItemsPriceUseCase getItemsPriceUseCase,
            ApplicationEventPublisher publisher
    ) {
        this.getItemsPriceUseCase = getItemsPriceUseCase;
        this.publisher = publisher;
    }

    @Override
    public Either<ErrorCoupon, MaximizeCouponResponse> maximizeCoupon(MaximizeCouponEntity couponRequest){
        ItemsId itemsId = couponRequest.getFavoritesItems();

        publisher.publishEvent(itemsId);

        List<Item> itemsPrice = getItemsPrice(itemsId);

        double amountCoupon = couponRequest.getCoupon().getValue();

        MaximizeCouponResponse resultSum = twoPointersApproach(itemsPrice, amountCoupon);

        return Either.right(resultSum);
    }

    public MaximizeCouponResponse twoPointersApproach(List<Item> itemsPrice, double amountCoupon) {
        double closestSum = Double.MAX_VALUE;
        List<Item> closestItems = new ArrayList<>();

        int left = 0, right = 0;
        double currentSum = 0;

        while (right < itemsPrice.size()) {
            currentSum += itemsPrice.get(right).getPrice();

            if (currentSum > amountCoupon) {
                while (currentSum > amountCoupon && left <= right) {
                    currentSum -= itemsPrice.get(left).getPrice();
                    left++;
                }
            }

            if (Math.abs(amountCoupon - currentSum) < Math.abs(amountCoupon - closestSum)) {
                closestSum = currentSum;
                closestItems = new ArrayList<>(itemsPrice.subList(left, right + 1));
            }

            right++;
        }

        return builderMaximizeCouponResponse(closestItems, closestSum);
    }

    private MaximizeCouponResponse builderMaximizeCouponResponse(List<Item> itemsInSum, double resultSum) {
        return MaximizeCouponResponse
                .builder()
                .itemIds(itemsInSum.stream().map(Item::getId).collect(Collectors.toList()))
                .total(resultSum)
                .build();
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
