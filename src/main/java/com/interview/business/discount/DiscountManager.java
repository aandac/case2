package com.interview.business.discount;

import com.interview.model.Campaign;
import com.interview.model.CartItem;
import com.interview.model.Coupon;
import java.util.List;

public interface DiscountManager {

  Double getCouponDiscount(Double cartTotalAmount);

  Double getCampaignDiscount(List<CartItem> cartItems);

  void applyCampaigns(Campaign[] campaigns);

  void applyCoupon(Coupon coupon);
}
