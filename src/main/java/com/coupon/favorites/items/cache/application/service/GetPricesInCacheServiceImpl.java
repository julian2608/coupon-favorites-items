package com.coupon.favorites.items.cache.application.service;

import com.coupon.favorites.items.cache.domain.service.CacheRepository;
import com.coupon.favorites.items.cache.domain.service.GetPricesInCacheService;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetPricesInCacheServiceImpl implements GetPricesInCacheService {

    private final CacheRepository cacheRepository;

    public GetPricesInCacheServiceImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }
    @Override
    public List<Item> getPriceItemsByIds(List<String> ids) {
        try {
            return cacheRepository.getPriceItemsByIds(ids);
        } catch (Exception e) {
            return List.of();
        }
    }
}
