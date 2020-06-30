package com.trendyol.interview;

import com.trendyol.interview.business.DeliveryCostCalculator;
import com.trendyol.interview.business.ShoppingCart;
import com.trendyol.interview.model.Campaign;
import com.trendyol.interview.model.Category;
import com.trendyol.interview.model.Coupon;
import com.trendyol.interview.model.DiscountType;
import com.trendyol.interview.model.Product;

public class ECommerceRunner {

  public static void main(String[] args) {
    Category food = new Category("food");
    Product apple = new Product("Apple", 100.0, food);
    Product almond = new Product("Almond", 150.0, food);

    ShoppingCart cart = new ShoppingCart();
    cart.addItem(apple, 3);
    cart.addItem(almond, 1);

    Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
    Campaign campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
    Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);

    cart.applyDiscounts(campaign1, campaign2, campaign3);

    Coupon coupon = new Coupon(100, 10, DiscountType.Rate);
    cart.applyCoupon(coupon);

    cart.print();

  }

}
