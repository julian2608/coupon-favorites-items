package com.coupon.favorites.items.cache.infrastructure;

import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import com.coupon.favorites.items.item.application.service.GetItemsPriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        CacheRepositoryImpl.class
})
class CacheRepositoryImplTest {

    @Autowired
    private CacheRepositoryImpl cacheRepository;

    @MockBean
    private RedisTemplate<String, Item> redisTemplate;

    @MockBean
    private ValueOperations<String, Item> valueOperations;

    private Collection<Item> items = Arrays.asList(new Item(), new Item(), new Item());

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void getPriceItemsByIds_When_Ok() {
        when(valueOperations.multiGet(Arrays.asList("1", "2", "3"))).thenReturn(Arrays.asList(new Item(), new Item(), new Item()));

        List<Item> items = cacheRepository.getPriceItemsByIds(Arrays.asList("1", "2", "3"));

        assertEquals(3, items.size());
        verify(valueOperations, times(1)).multiGet(Arrays.asList("1", "2", "3"));
    }

    @Test
    void getPriceItemsByIds_When_One_Item_Null() {
        when(valueOperations.multiGet(Arrays.asList("1", "2", "3"))).thenReturn(Arrays.asList(new Item(), new Item(), null));

        List<Item> items = cacheRepository.getPriceItemsByIds(Arrays.asList("1", "2", "3"));

        assertEquals(2, items.size());
        verify(valueOperations, times(1)).multiGet(Arrays.asList("1", "2", "3"));
    }

    @Test
    void savePriceItems_When_Ok() {
        doNothing().when(valueOperations).set(any(), any(), any());

        cacheRepository.savePriceItems(items);

        verify(valueOperations, times(3)).set(any(), any(), any());
    }

    @Test
    void savePriceItems_When_Collection_Empty() {
        cacheRepository.savePriceItems(List.of());

        verify(valueOperations, times(0)).set(any(), any(), any());
    }

}