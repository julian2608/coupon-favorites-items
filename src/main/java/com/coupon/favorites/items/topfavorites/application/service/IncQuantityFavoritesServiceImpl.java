package com.coupon.favorites.items.topfavorites.application.service;

import com.coupon.favorites.items.topfavorites.application.callable.IncQuantityFavoritesCallable;
import com.coupon.favorites.items.topfavorites.domain.service.IncQuantityFavoritesService;
import com.coupon.favorites.items.topfavorites.domain.service.ItemFavoriteRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class IncQuantityFavoritesServiceImpl implements IncQuantityFavoritesService {

    private final ItemFavoriteRepository itemFavoriteRepository;

    @Value("${app.meli.public.api.size-batch-update}")
    private static final int SIZE_BATCH = 5;
    public IncQuantityFavoritesServiceImpl(
            ItemFavoriteRepository itemFavoriteRepository
    ) {
        this.itemFavoriteRepository = itemFavoriteRepository;
    }

    @Override
    public void multiIncrementQuantity(Collection<String> favoritesItems) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {

            executorService.invokeAll(createTasks(favoritesItems));

        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Callable<Integer>> createTasks(Collection<String> favoritesItems) {
        double maxThreads = Math.ceil((double) favoritesItems.size() / (double) SIZE_BATCH);
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < maxThreads; i++) {
            tasks.add(new IncQuantityFavoritesCallable(getBatch(favoritesItems), itemFavoriteRepository));
        }
        return tasks;
    }

    private Collection<String> getBatch(Collection<String> itemsId) {
        Set<String> batchResult = new HashSet<>();

        itemsId.stream()
                .limit(Math.min(SIZE_BATCH, itemsId.size()))
                .forEach(batchResult::add);

        itemsId.removeAll(batchResult);

            return batchResult;
    }
}
