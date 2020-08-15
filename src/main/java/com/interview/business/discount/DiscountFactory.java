package com.interview.business.discount;

import com.interview.business.discount.type.FixedAmountDiscount;
import com.interview.business.discount.type.PercentageDiscount;

public final class DiscountFactory {

  private DiscountFactory(){}

  public static Discount createDiscount(Double discount, DiscountType discountType) {
    if (discountType == DiscountType.Amount) {
      return new FixedAmountDiscount(discount);
    } else if (discountType == DiscountType.Rate) {
      return new PercentageDiscount(discount);
    } else {
      throw new IllegalArgumentException("Invalid discount type received.");
    }
  }

}
