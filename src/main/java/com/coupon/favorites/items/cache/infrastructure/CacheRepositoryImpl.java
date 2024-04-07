package com.coupon.favorites.items.cache.infrastructure;

import com.coupon.favorites.items.cache.domain.service.CacheRepository;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
public class CacheRepositoryImpl implements CacheRepository {

    private final RedisTemplate<String, Item> redisTemplate;

    private final long ttl ;

    public CacheRepositoryImpl(
            RedisTemplate<String, Item> redisTemplate,
            @Value("${spring.data.redis.ttl.seconds:350}") long ttl
    ) {
        this.redisTemplate = redisTemplate;
        this.ttl = ttl;
    }

    @Override
    public List<Item> getPriceItemsByIds(Collection<String> ids) {
        List<Item> items = new ArrayList<>();

        List<Item> redisItems = redisTemplate.opsForValue().multiGet(ids);

        if (Objects.nonNull(redisItems)) {
            items = redisItems.stream().filter(Objects::nonNull).toList();
        }

        return items;
    }

    @Override
    public void savePriceItems(Collection<Item> ids) {
        for (Item item : ids) {
            redisTemplate.opsForValue().set(item.getId(), item, Duration.ofSeconds(ttl));
        }
    }
}
