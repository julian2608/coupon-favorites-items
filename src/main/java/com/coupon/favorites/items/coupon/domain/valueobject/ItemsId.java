package com.coupon.favorites.items.coupon.domain.valueobject;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.shared.exception.ValidationDataException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsId {

    private Set<String> value;

    public ItemsId(Set<String> value) {
        if (value.isEmpty()) {
            throw new ValidationDataException(ErrorCoupon.ErrorEmptyList.getMessage());
        }

        this.value = value;
    }
    public void clear () {
        this.value = null;
    }

}
