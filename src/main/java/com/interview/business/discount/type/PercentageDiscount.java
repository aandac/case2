package com.interview.business.discount.type;

import com.interview.business.discount.Discount;

public final class PercentageDiscount implements Discount {

    private final Double percentage;

    public PercentageDiscount(Double percentage) {
        this.percentage = percentage;
    }

    @Override
    public final Double calculateDiscount(Double amount) {
        return amount * percentage / 100;
    }

}
