package com.coupon.favorites.items.maximizedcoupon.domain.event;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class IncCountFavoritesValueEvent {
    private Set<String> itemsId;
}
