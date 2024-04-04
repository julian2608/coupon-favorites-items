package com.coupon.favorites.items.cache.application.config;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfig {
    @Bean
    public RedisTemplate<String, Item> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Item> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new ItemRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }
}