package com.coupon.favorites.items.coupon.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
public class IncCountFavoritesValueEvent {
    private Set<String> itemsId;
}
