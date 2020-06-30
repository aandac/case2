package com.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coupon {

  private Integer minPurchaseAmount;
  private Double discount;
  private DiscountType discountType;

}
