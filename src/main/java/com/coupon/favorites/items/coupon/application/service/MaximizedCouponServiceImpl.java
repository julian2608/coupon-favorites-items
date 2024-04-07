package com.coupon.favorites.items.coupon.application.service;

import com.coupon.favorites.items.cache.domain.usecase.GetPricesInCacheUseCase;
import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.coupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.coupon.domain.event.IncCountFavoritesValueEvent;
import com.coupon.favorites.items.coupon.domain.event.SaveCachePricesValueEvent;
import com.coupon.favorites.items.coupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.item.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import io.vavr.control.Either;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaximizedCouponServiceImpl implements MaximizeCouponService {

    private final GetItemsPriceUseCase getItemsPriceUseCase;

    private final GetPricesInCacheUseCase getPricesInCacheUseCase;

    private final ApplicationEventPublisher publisher;

    public MaximizedCouponServiceImpl(
            GetItemsPriceUseCase getItemsPriceUseCase,
            ApplicationEventPublisher publisher,
            GetPricesInCacheUseCase getPricesInCacheUseCase
    ) {
        this.getItemsPriceUseCase = getItemsPriceUseCase;
        this.publisher = publisher;
        this.getPricesInCacheUseCase = getPricesInCacheUseCase;
    }

    @Override
    public Either<ErrorCoupon, MaximizeCouponResponse> maximizeCoupon(MaximizeCouponEntity couponRequest){
        try {
            double amountCoupon = couponRequest.getCoupon().getValue();
            Set<String> itemsId = couponRequest.getFavoritesItems().getValue();

            publisher.publishEvent(IncCountFavoritesValueEvent.builder().itemsId(itemsId).build());

            List<Item> itemsPrice = getItemsPrice(itemsId);

            MaximizeCouponResponse resultSum = twoPointersApproach(itemsPrice, amountCoupon);

            couponRequest.clear();
            return Either.right(resultSum);
        }catch (Exception e) {
            return Either.left(ErrorCoupon.ErrorMaximizedCoupon(e.getMessage()));
        }
    }

    private MaximizeCouponResponse twoPointersApproach(List<Item> itemsPrice, double amountCoupon) {
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

    private List<Item> getItemsPrice(Set<String> itemsId) {

        List<Item> itemsPriceInCache = getPricesCache(itemsId);

        List<Item> result = new ArrayList<>(itemsPriceInCache);

        if (itemsId.size() == itemsPriceInCache.size()) {
            return itemsPriceInCache;
        }

        Set<String> itemsIdNotInCache = getPricesNotCached(itemsPriceInCache, itemsId);

        List<Item> itemsPriceObtained = getPricesCallingApi(itemsIdNotInCache);

        result.addAll(itemsPriceObtained);

        publisher.publishEvent(SaveCachePricesValueEvent.builder().items(itemsPriceObtained).build());

        return result;
    }

    private Set<String> getPricesNotCached (List<Item> itemsPriceInCache , Set<String> itemsId)
    {
        Set<String> itemsIdNotInCache = new HashSet<>(itemsId);

        for (Item item : itemsPriceInCache) {
            itemsIdNotInCache.remove(item.getId());
        }

        return itemsIdNotInCache;
    }


    private List<Item> getPricesCache (Set<String> itemsIdNotInCache)
    {
        return getPricesInCacheUseCase.execute(itemsIdNotInCache
                .stream()
                .toList()
        );
    }

    private List<Item> getPricesCallingApi (Set<String> itemsIdNotInCache){
        return getItemsPriceUseCase.execute(itemsIdNotInCache).fold(
                error -> {
                    throw new RuntimeException(
                            String.format("Detail: %s. Code status: %s",error.getMessage(), error.getCode().value()));
                },
                value -> value
        );
    }
}

