package com.coupon.favorites.items.item.application.service;

import com.coupon.favorites.items.MockCallFactory;
import com.coupon.favorites.items.apimeli.domain.service.MeliPublicApiService;
import com.coupon.favorites.items.item.domain.entity.ItemPriceResponse;
import com.coupon.favorites.items.coupon.domain.valueobject.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        GetItemsPriceServiceImpl.class
})
@TestPropertySource(locations = "classpath:application-test.properties")
class GetItemsPriceServiceImplTest {

    @Autowired
    private GetItemsPriceServiceImpl getItemsPriceService;
    @MockBean
    private MeliPublicApiService meliPublicApiService;
    private Set<String> itemsId;
    private List<ItemPriceResponse> responseApiMeli;

    @Value("${app.meli.public.api.size-batch-item:2}")
    private int sizeBatch;

    @BeforeEach
    public void init (){

        itemsId = new HashSet<>(new ArrayList<>(List.of("MCO32175689","MCO543789","MCO432765","MCO4354765","MCO4328796","MCO4323245","MCO4327568","MCO4323187","MCO4323472","MCO4326479")));

        responseApiMeli = itemsId.stream().map(item -> {
            return ItemPriceResponse
                    .builder()
                    .code(200)
                    .body(
                            Item.builder().id(item).price(Double.parseDouble(item.substring(item.length()-4))).build())
                    .build();
        }).toList();
    }

    @Test
    public void getItemPricesBatchAsync_When_Ok() {
        Call<List<ItemPriceResponse>> callApiMeliPrices = MockCallFactory.getMockCallWithResponse(responseApiMeli);

        when(meliPublicApiService.getItemsPrice(anyString(), anyString())).thenReturn(
                callApiMeliPrices
        );


        List<Item> prices = getItemsPriceService.getItemsPrice(itemsId).fold(
                error -> new ArrayList<>(),
                items -> items
        );

        verify(meliPublicApiService, times(5)).getItemsPrice(anyString(), anyString());
        assertEquals(50, prices.size());
    }

    @Test
    public void getItemPricesBatchAsync_When_Items_Empty() {
        List<Item> prices = getItemsPriceService.getItemsPrice(new HashSet<>()).fold(
                error -> new ArrayList<>(),
                items -> items
        );

        verify(meliPublicApiService, times(0)).getItemsPrice(anyString(), anyString());
        assertEquals(0, prices.size());
    }

    @Test
    public void getItemPricesBatchAsync_When_Api_Meli_Exception() {
        doThrow(new RuntimeException("Api meli error")).when(meliPublicApiService).getItemsPrice(anyString(), anyString());


        assertThrows( RuntimeException.class, () -> {
            getItemsPriceService.getItemsPrice(itemsId).fold(
                    error -> new ArrayList<>(),
                    items -> items
            );
        });
    }
}