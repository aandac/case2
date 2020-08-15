package com.interview.model;

import com.interview.business.discount.Discount;
import com.interview.business.discount.DiscountFactory;
import com.interview.business.discount.DiscountType;

public class Coupon {

  private final Double minPurchaseAmount;
  private final Discount discountStrategy;

  public Coupon(Double minPurchaseAmount, Double discount, DiscountType discountType) {
    this.minPurchaseAmount = minPurchaseAmount;
    this.discountStrategy = DiscountFactory.createDiscount(discount, discountType);
  }

  public Double calculateCouponDiscount(Double totalAmount) {
    return discountStrategy.calculateDiscount(totalAmount);
  }

  public Double getMinPurchaseAmount() {
    return minPurchaseAmount;
  }

}
