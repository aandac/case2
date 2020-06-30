package com.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Campaign {

  private Category category;
  private Double discount;
  private Integer requiredQuantity;
  private DiscountType discountType;

}
