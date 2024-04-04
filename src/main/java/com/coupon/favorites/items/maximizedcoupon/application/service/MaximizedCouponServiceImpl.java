package com.coupon.favorites.items.maximizedcoupon.application.service;

import com.coupon.favorites.items.cache.domain.usecase.GetPricesInCacheUseCase;
import com.coupon.favorites.items.cache.domain.usecase.SavePricesInCacheUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponEntity;
import com.coupon.favorites.items.maximizedcoupon.domain.entity.MaximizeCouponResponse;
import com.coupon.favorites.items.maximizedcoupon.domain.service.MaximizeCouponService;
import com.coupon.favorites.items.itemsprice.domain.usecase.GetItemsPriceUseCase;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.vavr.control.Either;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MaximizedCouponServiceImpl implements MaximizeCouponService {

    private final GetItemsPriceUseCase getItemsPriceUseCase;

    private final SavePricesInCacheUseCase savePricesInCacheUseCase;

    private final GetPricesInCacheUseCase getPricesInCacheUseCase;

    private final ApplicationEventPublisher publisher;

    public MaximizedCouponServiceImpl(
            GetItemsPriceUseCase getItemsPriceUseCase,
            ApplicationEventPublisher publisher,
            SavePricesInCacheUseCase savePricesInCacheUseCase,
            GetPricesInCacheUseCase getPricesInCacheUseCase
    ) {
        this.getItemsPriceUseCase = getItemsPriceUseCase;
        this.publisher = publisher;
        this.savePricesInCacheUseCase = savePricesInCacheUseCase;
        this.getPricesInCacheUseCase = getPricesInCacheUseCase;
    }

    @Override
    public Either<ErrorCoupon, MaximizeCouponResponse> maximizeCoupon(MaximizeCouponEntity couponRequest) throws JsonProcessingException {
        ItemsId itemsId = couponRequest.getFavoritesItems();

        publisher.publishEvent(itemsId);

        List<Item> itemsPrice = getItemsPrice(itemsId);

        double amountCoupon = couponRequest.getCoupon().getValue();

        MaximizeCouponResponse resultSum = twoPointersApproach(itemsPrice, amountCoupon);

        couponRequest.getFavoritesItems().setValue(null);
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

    private List<Item> getItemsPrice(ItemsId itemsId) {

        List<Item> itemsPriceInCache = getPricesCache(itemsId.getValue());

        List<Item> result = new ArrayList<>(itemsPriceInCache);

        if (itemsId.getValue().size() == itemsPriceInCache.size()) {
            return itemsPriceInCache;
        }

        Set<String> itemsIdNotInCache = getPricesNotCached(itemsPriceInCache, itemsId);

        List<Item> itemsPriceObtained = getPricesCallingApi(itemsIdNotInCache);

        result.addAll(itemsPriceObtained);

        savePricesInCacheUseCase.execute(itemsPriceObtained);

        return result;
    }

    private Set<String> getPricesNotCached (List<Item> itemsPriceInCache , ItemsId itemsId)
    {
        Set<String> itemsIdNotInCache = new HashSet<>(itemsId.getValue());

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
        return getItemsPriceUseCase.execute(new ItemsId(itemsIdNotInCache)).fold(
                error -> {
                    throw new RuntimeException(
                            String.format("Detail: %s. Code status: %s",error.getMessage(), error.getCode().value()));
                },
                value -> value
        );
    }
}
