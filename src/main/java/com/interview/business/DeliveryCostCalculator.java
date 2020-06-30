package com.interview.business;

import com.interview.model.Order;
import com.interview.model.Product;
import com.interview.model.Category;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeliveryCostCalculator {

  private final Double costPerDelivery;
  private final Double costPerProduct;
  private final Double fixedCost;


  public Double calculateFor(ShoppingCart cart) {
    List<Order> cartOrders = cart.getCartOrders();
    Map<Category, List<Order>> groupedOrders = cartOrders.stream()
        .collect(Collectors.groupingBy(order -> order.getProduct().getCategory()));
    Map<Product, List<Order>> groupedProducts = cartOrders.stream()
        .collect(Collectors.groupingBy(Order::getProduct));

    int numberOfDeliveries = groupedOrders.size();
    int numberOfProducts = groupedProducts.size();
    return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;
  }

}
