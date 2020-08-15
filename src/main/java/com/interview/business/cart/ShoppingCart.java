package com.interview.business.cart;

import com.interview.business.delivery.DeliveryCostManager;
import com.interview.business.delivery.DeliveryCostManagerImpl;
import com.interview.business.discount.DiscountManager;
import com.interview.business.discount.DiscountManagerImpl;
import com.interview.business.print.PrintManager;
import com.interview.business.print.PrintManagerImpl;
import com.interview.model.Campaign;
import com.interview.model.CartItem;
import com.interview.model.Coupon;
import com.interview.model.Product;
import java.util.List;

public class ShoppingCart {

  private final CartOperations cartOperations;
  private final DeliveryCostManager deliveryCostManager;
  private final DiscountManager discountManager;
  private final PrintManager printManager;

  public ShoppingCart() {
    this.deliveryCostManager = new DeliveryCostManagerImpl(1.0, 1.0, 2.99);
    this.printManager = new PrintManagerImpl();
    this.discountManager = new DiscountManagerImpl();
    this.cartOperations = new CartOperationsImpl();
  }


  public void addItem(Product product, Integer quantity) {
    cartOperations.addItem(product, quantity);
  }

  public void removeItem(Product product) {
    cartOperations.removeItem(product);
  }

  public Double getDeliveryCost() {
    return deliveryCostManager.getDeliveryCost(cartOperations.getCartItems());
  }

  public Double getCouponDiscount() {
    return discountManager.getCouponDiscount(cartOperations.getCartTotalAmountBeforeDiscount());
  }

  public Double getCampaignDiscount() {
    return discountManager.getCampaignDiscount(cartOperations.getCartItems());
  }

  public Double getTotalAmountAfterDiscounts() {
    List<CartItem> items = cartOperations.getCartItems();
    Double totalAmountBeforeDiscount = cartOperations.getCartTotalAmountBeforeDiscount();
    Double campaignDiscount = discountManager.getCampaignDiscount(items);
    Double couponDiscount = discountManager.getCouponDiscount(totalAmountBeforeDiscount);

    double total = totalAmountBeforeDiscount - campaignDiscount - couponDiscount;
    return Math.max(total, 0.0);
  }

  public void print() {
    List<CartItem> items = cartOperations.getCartItems();
    printManager.printCart(items);
    printManager.printDiscounts(getCampaignDiscount(), getCouponDiscount());
    printManager.printTotalAmountAfterDiscount(getTotalAmountAfterDiscounts());
    printManager.printDeliveryCost(getDeliveryCost());
  }

  public void applyCampaigns(Campaign... campaigns) {
    discountManager.applyCampaigns(campaigns);
  }

  public void applyCoupon(Coupon coupon) {
    discountManager.applyCoupon(coupon);
  }
}
