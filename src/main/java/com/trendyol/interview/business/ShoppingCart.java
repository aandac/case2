package com.trendyol.interview.business;

import com.trendyol.interview.model.Campaign;
import com.trendyol.interview.model.Category;
import com.trendyol.interview.model.Coupon;
import com.trendyol.interview.model.Order;
import com.trendyol.interview.model.Product;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

  private List<Order> cartOrders;
  private Double discount;
  private DeliveryCostCalculator deliveryCostCalculator;

  private NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));

  public ShoppingCart() {
    this.cartOrders = new ArrayList<>();
    this.discount = 0.0;
    deliveryCostCalculator = new DeliveryCostCalculator(1.0, 1.0, 2.99);

  }

  public boolean addItem(Product product, Integer quantity) {
    return this.cartOrders.add(new Order(product, quantity));
  }

  public void applyDiscounts(Campaign... campaigns) {

  }

  public void applyCoupon(Coupon coupon) {

  }

  public Double getTotalAmountAfterDiscounts() {
    return 0.0;
  }

  public Double getCouponDiscounts() {
    return 0.0;
  }

  public Double getCampaignDiscounts() {
    return 0.0;
  }

  public Double getDeliveryCost() {
    return deliveryCostCalculator.calculateFor(this);
  }

  public void print() {
    Map<Category, List<Order>> groupedProducts = cartOrders.stream()
        .collect(Collectors.groupingBy(item -> item.getProduct().getCategory()));
    System.out
        .format("%-12s \t%-12s \t%12s \t%12s \t%12s \t%12s\n", "Category", "Product Name",
            "Quantity", "Unit Price", "Total Price", "Total Discount");

    for (Category category : groupedProducts.keySet()) {
      List<Order> orders = groupedProducts.get(category);
      for (Order order : orders) {
        Product product = order.getProduct();
        System.out
            .format("%-12s \t%-12s \t%12s \t%12s \t%12s\n", category.getTitle(), product.getTitle(),
                order.getQuantity(), numberFormat
                    .format(product.getPrice()), numberFormat
                    .format(order.getQuantity() * product.getPrice()));
      }
    }
    System.out
        .format("\t\t\t\t\t%77s\n", numberFormat.format(getCampaignDiscounts()) + " (Campaign)");
    System.out.format("\t\t\t\t\t%75s\n", numberFormat.format(getCouponDiscounts()) + " (Coupon)");

    System.out.println("Total Amount :\t" + numberFormat.format(getTotalAmountAfterDiscounts()));
    System.out.println("Delivery Cost :\t" + numberFormat.format(getDeliveryCost()));

  }

}
