package com.trendyol.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coupon {

  private Integer minPurchaseAmount;
  private Integer discount;
  private DiscountType discountType;

}
