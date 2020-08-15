package com.interview.business.print;

import com.interview.model.CartItem;
import com.interview.model.Category;
import com.interview.model.Product;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintManagerImpl implements PrintManager {

  private final NumberFormat numberFormat = NumberFormat
      .getCurrencyInstance(new Locale("tr", "TR"));

  @Override
  public void printCart(List<CartItem> cartItemList) {
    Map<Category, List<CartItem>> groupedProducts = cartItemList.stream()
        .collect(Collectors.groupingBy(item -> item.getProduct().getCategory()));
    System.out
        .format("%-12s \t%-12s \t%12s \t%12s \t%12s \t%12s\n", "Category", "Product Name",
            "Quantity", "Unit Price", "Total Price", "Total Discount");

    for (Category category : groupedProducts.keySet()) {
      List<CartItem> cartItems = groupedProducts.get(category);
      for (CartItem cartItem : cartItems) {
        Product product = cartItem.getProduct();
        System.out
            .format("%-12s \t%-12s \t%12s \t%12s \t%12s\n", category.getTitle(), product.getTitle(),
                cartItem.getQuantity(), numberFormat
                    .format(product.getPrice()), numberFormat
                    .format(cartItem.getQuantity() * product.getPrice()));
      }
    }
  }

  @Override
  public void printDiscounts(Double campaignDiscount, Double couponDiscount) {
    System.out
        .format("\t\t\t\t\t%77s\n", numberFormat.format(campaignDiscount) + " (Campaign)");
    System.out.format("\t\t\t\t\t%75s\n", numberFormat.format(couponDiscount) + " (Coupon)");
  }

  @Override
  public void printTotalAmountAfterDiscount(Double totalAmountAfterDiscount) {
    System.out.println("Total Amount  :\t" + numberFormat.format(totalAmountAfterDiscount));
  }

  @Override
  public void printDeliveryCost(Double deliveryCost) {
    System.out.println("Delivery Cost :\t" + numberFormat.format(deliveryCost));
  }


}
