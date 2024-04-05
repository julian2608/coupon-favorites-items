package com.coupon.favorites.items.maximizedcoupon.domain.valueobject;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.shared.ValidationDataException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
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
    public ItemsId(Set<String> value) {
        if (value.isEmpty()) {
            throw new ValidationDataException(ErrorCoupon.ErrorEmptyList.getMessage());
        }

        this.value = value;
    }
    private Set<String> value;

    public String toQueryParam(){
        return String.join(",", value);
    }

    public void clear () {
        this.value = null;
    }

}
