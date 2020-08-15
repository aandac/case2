package com.interview.business.cart;

import com.interview.model.CartItem;
import com.interview.model.Product;
import java.util.List;

public interface CartOperations {

  void addItem(Product product, Integer quantity);

  void removeItem(Product product);

  List<CartItem> getCartItems();

  Double getCartTotalAmountBeforeDiscount();

}
