package com.trendyol.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Order {

  private Product product;
  private Integer quantity;



}
