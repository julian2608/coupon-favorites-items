package com.coupon.favorites.items.maximizedcoupon.domain.entity;

import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.Coupon;
import com.coupon.favorites.items.maximizedcoupon.domain.valueobject.ItemsId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MaximizeCouponEntity {

    @JsonProperty("amount")
    private Coupon coupon;

    @JsonProperty("item_ids")
    private ItemsId favoritesItems;

    public void setFavoritesItems(Set<String> favoritesItems) {
        this.favoritesItems = ItemsId
                .builder()
                .value(favoritesItems)
                .build();
    }


    public void setCoupon(long value) {
        this.coupon = Coupon
                .builder()
                .value(value)
                .build();
    }

    public void clear () {
        this.favoritesItems.clear();
    }
}
