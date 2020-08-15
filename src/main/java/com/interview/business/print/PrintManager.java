package com.interview.business.print;

import com.interview.model.CartItem;
import java.util.List;

public interface PrintManager {

  void printCart(List<CartItem> cartItems);

  void printDiscounts(Double campaignDiscount, Double couponDiscount);

  void printTotalAmountAfterDiscount(Double totalAmountAfterDiscount);

  void printDeliveryCost(Double totalAmountAfterDiscount);
}
