package com.interview.model;

import com.interview.business.discount.Discount;
import com.interview.business.discount.DiscountFactory;
import com.interview.business.discount.DiscountType;
import java.util.List;

public class Campaign {

  private final Category category;
  private final Discount discountStrategy;
  private final Integer requiredQuantity;

  public Campaign(Category category, Double discount, Integer requiredQuantity,
      DiscountType discountType) {
    this.category = category;
    this.requiredQuantity = requiredQuantity;
    this.discountStrategy = DiscountFactory.createDiscount(discount, discountType);
  }

  public Double calculateDiscount(List<CartItem> cartItemList) {
    int itemsInSameCategory = 0;
    Double categoryTotalAmount = 0.0;
    for (CartItem cartItem : cartItemList) {
      if (cartItem.getProduct().getCategory().isSameCategory(this.category)) {
        itemsInSameCategory += cartItem.getQuantity();
        categoryTotalAmount += cartItem.getQuantity() * cartItem.getProduct().getPrice();
      }
    }

    Double calculatedDiscount = 0.0;
    if (itemsInSameCategory >= requiredQuantity) {
      calculatedDiscount = discountStrategy.calculateDiscount(categoryTotalAmount);
    }

    return calculatedDiscount;
  }
}
