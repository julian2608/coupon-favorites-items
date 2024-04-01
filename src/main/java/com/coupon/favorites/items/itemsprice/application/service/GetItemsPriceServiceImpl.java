package com.coupon.favorites.items.itemsprice.application.service;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.itemsprice.domain.entity.ItemPriceResponse;
import com.coupon.favorites.items.itemsprice.domain.service.GetItemsPriceService;
import com.coupon.favorites.items.shared.service.MeliPublicApiService;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.coupon.favorites.items.shared.util.ApiCallExecutorCallable;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class GetItemsPriceServiceImpl implements GetItemsPriceService {
    private final MeliPublicApiService meliPublicApiService;

    private final static String ATTRIBUTES = "id,price";

    @Value("${app.meli.public.api.size-batch-item}")
    private final int SIZE_BATCH=5;

    public GetItemsPriceServiceImpl(MeliPublicApiService meliPublicApiService) {
        this.meliPublicApiService = meliPublicApiService;
    }

    @Override
    public Either<ErrorCoupon, List<Item>> getItemsPrice(ItemsId itemsId) {
        return Either.right(getItemPricesBatchAsync(itemsId));
    }

    private List<Item> getItemPricesBatchAsync(ItemsId itemsId) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            double maxThreads = Math.ceil((double) itemsId.getValue().size() / (double) SIZE_BATCH);

            List<Callable<List<ItemPriceResponse>>> tasks = new ArrayList<>();

            for (int i = 0; i < maxThreads; i++) {
                tasks.add(createTaskCallApi(itemsId));
            }

            List<Future<List<ItemPriceResponse>>> futures = executorService.invokeAll(tasks);

            return futureToListItem(futures);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ApiCallExecutorCallable createTaskCallApi(ItemsId itemsId) {
        return new ApiCallExecutorCallable<>(meliPublicApiService.getItemsPrice(getBatch(itemsId).toQueryParam(), ATTRIBUTES));
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


    private ItemsId getBatch(ItemsId itemsId) {
        Set<String> batchResult = new HashSet<>();

        itemsId.getValue().stream()
                .limit(SIZE_BATCH < itemsId.getValue().size() ? SIZE_BATCH: itemsId.getValue().size())
                .forEach(batchResult::add);

        itemsId.getValue().removeAll(batchResult);

        return ItemsId.builder().value(batchResult).build();
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
