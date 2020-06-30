package com.interview;

import com.interview.business.DeliveryCostCalculator;
import com.interview.business.ShoppingCart;
import com.interview.model.DiscountType;
import com.interview.model.Product;
import com.interview.model.Campaign;
import com.interview.model.Category;
import com.interview.model.Coupon;

public class ECommerceRunner {

  public static void main(String[] args) {
    Category food = new Category("food");
    Product apple = new Product("Apple", 100.0, food);
    Product almond = new Product("Almond", 150.0, food);
    Product orange = new Product("Orange", 90.0, food);

    DeliveryCostCalculator deliveryCostCalculator = new DeliveryCostCalculator(1.0, 1.0, 2.99);
    ShoppingCart cart = new ShoppingCart(deliveryCostCalculator);

    cart.addItem(apple, 3);
    cart.addItem(almond, 1);

    Campaign campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
    Campaign campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
    Campaign campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);

    cart.applyDiscounts(campaign1, campaign2, campaign3);

    Coupon coupon = new Coupon(100, 10.0, DiscountType.Rate);
    cart.applyCoupon(coupon);

    cart.print();
  }

}
