package com.interview.business.delivery;

import com.interview.model.CartItem;
import java.util.List;

public interface DeliveryCostManager {

  Double getDeliveryCost(List<CartItem> cartItems);

}
