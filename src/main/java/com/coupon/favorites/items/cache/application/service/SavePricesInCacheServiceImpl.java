package com.coupon.favorites.items.cache.application.service;

import com.coupon.favorites.items.cache.domain.service.CacheRepository;
import com.coupon.favorites.items.cache.domain.service.SavePricesInCacheService;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.item.domain.exception.ItemRepositoryException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SavePricesInCacheServiceImpl implements SavePricesInCacheService {

    private final CacheRepository cacheRepository;

    public SavePricesInCacheServiceImpl(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }
    @Override
    public void savePriceItems(Collection<Item> ids) {
        try {
            cacheRepository.savePriceItems(ids);
        }catch (Exception e) {
            throw new ItemRepositoryException(e);
        }
    }
}
