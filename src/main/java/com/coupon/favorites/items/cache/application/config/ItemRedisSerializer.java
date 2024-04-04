package com.coupon.favorites.items.cache.application.config;

import com.coupon.favorites.items.itemsprice.domain.entity.ErrorItemsPrice;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.Objects;

public class ItemRedisSerializer implements RedisSerializer<Item> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Item item) throws SerializationException {
        try {
            if (Objects.isNull(item.getId()) || Objects.isNull(item.getPrice())) {
                return null;
            }
            return objectMapper.writeValueAsBytes(item);
        } catch (Exception e) {
            throw new SerializationException(ErrorItemsPrice.errorSerializerItem().getMessage(), e);
        }
    }

    @Override
    public Item deserialize(byte[] bytes) throws SerializationException {
        try {
            return Objects.isNull(bytes) ? null : objectMapper.readValue(bytes, Item.class);
        } catch (Exception e) {
            throw new SerializationException(ErrorItemsPrice.errorDeserializerItem().getMessage(), e);
        }
    }
}