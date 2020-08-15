package com.interview.business.discount.type;

import com.interview.business.discount.Discount;

public final class FixedAmountDiscount implements Discount {

    private final Double fixedDiscountAmount;

    public FixedAmountDiscount(double fixedDiscountAmount) {
        this.fixedDiscountAmount = fixedDiscountAmount;
    }

    @Override
    public final Double calculateDiscount(Double amount) {
        return fixedDiscountAmount;
    }

}
