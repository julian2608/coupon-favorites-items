package com.coupon.favorites.items.coupon.domain.valueobject;

import com.coupon.favorites.items.coupon.domain.entity.ErrorCoupon;
import com.coupon.favorites.items.shared.exception.ValidationDataException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
