package com.interview.business.delivery;

import com.interview.model.CartItem;
import com.interview.model.Category;
import com.interview.model.Product;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeliveryCostManagerImpl implements DeliveryCostManager {

  private final Double costPerDelivery;
  private final Double costPerProduct;
  private final Double fixedCost;

  @Override
  public Double getDeliveryCost(List<CartItem> cartItems) {
    if (cartItems.isEmpty()) {
      return 0.0;
    }
    // Number of deliveries is calculated by the number of distinct categories in the cart
    Map<Category, List<CartItem>> groupedByCategory = cartItems.stream()
        .collect(Collectors.groupingBy(cartItem -> cartItem.getProduct().getCategory()));

    // Number of products is the number of different products in the cart
    Map<Product, List<CartItem>> groupedByProducts = cartItems.stream()
        .collect(Collectors.groupingBy(CartItem::getProduct));

    int numberOfDeliveries = groupedByCategory.size();
    int numberOfProducts = groupedByProducts.size();
    return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;
  }

}
