package com.interview.business.cart;

import com.interview.model.CartItem;
import com.interview.model.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartOperationsImpl implements CartOperations {

  private final List<CartItem> cartItems;

  public CartOperationsImpl() {
    this.cartItems = new ArrayList<>();
  }

  public Double getCartTotalAmountBeforeDiscount() {
    return cartItems
        .stream()
        .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
        .sum();
  }

  @Override
  public void addItem(Product product, Integer quantity) {
    Optional<CartItem> existItem = this.cartItems.stream()
        .filter(ci -> ci.getProduct().equals(product))
        .findAny();
    if (existItem.isPresent()) {
      CartItem item = existItem.get();
      item.setQuantity(item.getQuantity() + quantity);
    } else {
      this.cartItems.add(new CartItem(product, quantity));
    }
  }

  @Override
  public void removeItem(Product product) {
    this.cartItems.stream()
        .filter(ci -> ci.getProduct().equals(product))
        .findAny()
        .ifPresent(this.cartItems::remove);
  }

  @Override
  public List<CartItem> getCartItems() {
    return cartItems;
  }

}
