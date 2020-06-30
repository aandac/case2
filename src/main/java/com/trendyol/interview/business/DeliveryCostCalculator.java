package com.trendyol.interview.business;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class DeliveryCostCalculator {

  private Double costPerDelivery;
  private Double costPerProduct;
  private Double fixedCost;


  public Double calculateFor(ShoppingCart cart) {
    return 0.0;
  }

}
