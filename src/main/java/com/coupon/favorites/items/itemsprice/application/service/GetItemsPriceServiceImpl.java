package com.coupon.favorites.items.itemsprice.application.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.itemsprice.domain.entity.ItemPriceResponse;
import com.coupon.favorites.items.itemsprice.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.apimeli.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.coupon.favorites.items.shared.util.ApiCallExecutorCallable;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
public class GetItemsPriceServiceImpl implements GetItemsPriceService {
    private final MeliPublicApiService meliPublicApiService;

    private final static String ATTRIBUTES = "id,price";

    private final int SIZE_BATCH;

    public GetItemsPriceServiceImpl(
            MeliPublicApiService meliPublicApiService,
            @Value("${app.meli.public.api.size-batch-item:20}") int SIZE_BATCH
            ) {
        this.meliPublicApiService = meliPublicApiService;
        this.SIZE_BATCH = SIZE_BATCH;
    }

    @Override
    public Either<ErrorCoupon, List<Item>> getItemsPrice(ItemsId itemsId) {
        return Either.right(getItemPricesBatchAsync(itemsId));
    }

    private List<Item> getItemPricesBatchAsync(ItemsId itemsId) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<List<ItemPriceResponse>>> futures = executorService.invokeAll(getBatchTask(itemsId));


            executorService.shutdown();
        return futureToListItem(futures);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ApiCallExecutorCallable createTaskCallApi(ItemsId itemsId) {
        return new ApiCallExecutorCallable<>(meliPublicApiService.getItemsPrice(itemsId.toQueryParam(), ATTRIBUTES));
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
                .collect(Collectors.toList());
    }


    private List<Callable<List<ItemPriceResponse>>> getBatchTask(ItemsId itemsId) {
        List<Callable<List<ItemPriceResponse>>> newResult = new ArrayList<>();
        List<String> batchResult = new ArrayList<>();

        int countGroups = 0;
        for (String value : itemsId.getValue()) {
            batchResult.add(value);
            countGroups++;

            if (countGroups == SIZE_BATCH) {
                newResult.add(createTaskCallApi(new ItemsId(new HashSet<>(batchResult))));
                batchResult.clear();
                countGroups = 0;
            }
        }

        if (!batchResult.isEmpty()) {
            newResult.add(createTaskCallApi(new ItemsId(new HashSet<>(batchResult))));
        }

        return newResult;
    }

    /*public void test(){

        for (int i = 0; i <= 400; i++) {
            try {
                JsonNode rootNode = ApiCallExecutor.execute(meliPublicApiService.getIds(i, "MCO1747"));
                JsonNode resultsNode = rootNode.path("results");
                for (JsonNode resultNode : resultsNode) {
                    String id = resultNode.path("id").asText();
                    System.out.println(id);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
