package com.coupon.favorites.items.cache.infrastructure;

import com.coupon.favorites.items.cache.domain.service.CacheRepository;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
public class CacheRepositoryImpl implements CacheRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String  KEY = "item";

    private static final long TTL = 300L;

    public CacheRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Item> getPriceItemsByIds(Collection<String> ids) {

        List<Object> items = redisTemplate.opsForHash().multiGet(KEY, Arrays.asList(ids.toArray()));

        return items.stream()
                .filter(Objects::nonNull)
                .map(item -> {
                    try {
                        return new Item(item.toString());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public void savePriceItems(Collection<Item> ids) {
        Map<String, String> itemPrices = ids.stream()
                .collect(Collectors.toMap(Item::getId, Item::toString));

        redisTemplate.opsForHash().putAll(KEY, itemPrices);
        redisTemplate.expire(KEY, TTL, TimeUnit.SECONDS);
    }
}
