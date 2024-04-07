package com.coupon.favorites.items.item.application.service;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.item.domain.entity.ItemPriceResponse;
import com.coupon.favorites.items.item.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.apimeli.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.shared.util.ApiCallExecutorCallable;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class GetItemsPriceServiceImpl implements GetItemsPriceService {
    private final MeliPublicApiService meliPublicApiService;

    @Value("${app.meli.public.api.attributes_query}")
    private String attributes;

    @Value("${app.meli.public.api.size-batch-item:20}")
    private int sizeBatch;

    public GetItemsPriceServiceImpl(
            MeliPublicApiService meliPublicApiService
            ) {
        this.meliPublicApiService = meliPublicApiService;
    }

    @Override
    public Either<ErrorCoupon, List<Item>> getItemsPrice(Set<String> itemsId) {
        return Either.right(getItemPricesBatchAsync(itemsId));
    }

    private List<Item> getItemPricesBatchAsync(Set<String> itemsId) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<List<ItemPriceResponse>>> futures = executorService.invokeAll(getBatchTask(itemsId));

            executorService.shutdown();

            return futureToListItem(futures);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ApiCallExecutorCallable createTaskCallApi(Set<String> itemsId) {
        return new ApiCallExecutorCallable<>(meliPublicApiService.getItemsPrice(String.join(",",itemsId), attributes));
    }

    private List<Item> futureToListItem( List<Future<List<ItemPriceResponse>>> futureItems) {
        List<ItemPriceResponse> resultList = new ArrayList<>();
        try {
            for (Future<List<ItemPriceResponse>> future : futureItems) {
                resultList.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return resultList.stream()
                .map(ItemPriceResponse::getBody)
                .filter(item -> Objects.nonNull(item.getPrice()))
                .collect(Collectors.toList());
    }

    private List<Callable<List<ItemPriceResponse>>> getBatchTask(Set<String> itemsId) {
        List<Callable<List<ItemPriceResponse>>> newResult = new ArrayList<>();
        List<String> batchResult = new ArrayList<>();

        int countGroups = 0;
        for (String value : itemsId) {
            batchResult.add(value);
            countGroups++;

            if (countGroups == sizeBatch) {
                newResult.add(createTaskCallApi(new HashSet<>(batchResult)));
                batchResult.clear();
                countGroups = 0;
            }
        }

        if (!batchResult.isEmpty()) {
            newResult.add(createTaskCallApi(new HashSet<>(batchResult)));
        }

        return newResult;
    }
}
