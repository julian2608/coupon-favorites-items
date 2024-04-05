package com.coupon.favorites.items.maximizedcoupon.domain.valueobject;

import com.coupon.favorites.items.maximizedcoupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.shared.ValidationDataException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Coupon {
    public Coupon(double value) {
        if (value <= 0) {
            throw new ValidationDataException(ErrorCoupon.ErrorValueCouponMin.getMessage());
        }

        this.value = value;
    }
    private double value;

}
