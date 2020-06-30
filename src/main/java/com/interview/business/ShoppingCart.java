package com.interview.business;

import com.interview.model.DiscountType;
import com.interview.model.Order;
import com.interview.model.Product;
import com.interview.model.Campaign;
import com.interview.model.Category;
import com.interview.model.Coupon;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

  private final List<Order> cartOrders;
  private final DeliveryCostCalculator deliveryCostCalculator;

  private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
  private Campaign[] campaigns;
  private Coupon coupon;

  private Double campaignDiscount;
  private Double couponDiscount;

  public ShoppingCart(DeliveryCostCalculator deliveryCostCalculator) {
    this.deliveryCostCalculator = deliveryCostCalculator;
    this.cartOrders = new ArrayList<>();
    this.campaignDiscount=0.0;
    this.couponDiscount=0.0;
  }

  private double getTotalCart() {
    return cartOrders
        .stream()
        .mapToDouble(order -> order.getQuantity() * order.getProduct().getPrice())
        .sum();
  }


  private void calculateCouponDiscount() {
    if (coupon == null) {
      couponDiscount = 0.0;
      return;
    }
    double totalAmountAfterCampaignDiscount = getTotalCart() - getCampaignDiscounts();
    if (totalAmountAfterCampaignDiscount < coupon.getMinPurchaseAmount()) {
      couponDiscount = 0.0;
      return;
    }

    if (coupon.getDiscountType() == DiscountType.Rate) {
      couponDiscount = totalAmountAfterCampaignDiscount * coupon.getDiscount() / 100;
    } else if (coupon.getDiscountType() == DiscountType.Amount) {
      couponDiscount = coupon.getDiscount();
    } else {
      throw new IllegalArgumentException("Invalid discount type " + coupon.getDiscountType());
    }
  }

  private void calculateCampaignDiscount() {
    if (campaigns == null || campaigns.length == 0) {
      campaignDiscount = 0.0;
      return;
    }

    Map<Category, List<Order>> groupedProducts = cartOrders.stream()
        .collect(Collectors.groupingBy(item -> item.getProduct().getCategory()));
    for (Campaign campaign : campaigns) {
      List<Order> orders = groupedProducts.get(campaign.getCategory());
      int totalQuantityInCategory = orders.stream().mapToInt(Order::getQuantity).sum();
      if (totalQuantityInCategory <= campaign.getRequiredQuantity()) {
        continue;
      }

      double categoryDiscount = 0.0;

      if (campaign.getDiscountType() == DiscountType.Rate) {
        double categoryTotalSum = orders.stream()
            .mapToDouble(order -> order.getQuantity() * order.getProduct().getPrice())
            .sum();
        categoryDiscount = categoryTotalSum * campaign.getDiscount() / 100;
      } else if (campaign.getDiscountType() == DiscountType.Amount) {
        categoryDiscount = campaign.getDiscount();
      } else {
        throw new IllegalArgumentException("Invalid discount type " + campaign.getDiscountType());
      }

      if (categoryDiscount >= campaignDiscount) {
        campaignDiscount = categoryDiscount;
      }
    }
  }


  public void addItem(Product product, Integer quantity) {
    this.cartOrders.add(new Order(product, quantity));
    calculateCampaignDiscount();
    calculateCouponDiscount();
  }

  public void applyDiscounts(Campaign... campaigns) {
    this.campaigns = campaigns;
    calculateCampaignDiscount();
  }

  public void applyCoupon(Coupon coupon) {
    this.coupon = coupon;
    calculateCouponDiscount();
  }

  public Double getTotalAmountAfterDiscounts() {
    double total = getTotalCart() - getCampaignDiscounts() - getCouponDiscounts();
    return Math.max(total, 0.0);
  }

  public Double getCouponDiscounts() {
    return couponDiscount;
  }

  /**
   * Cart should apply the maximum amount of discount to the cart
   *
   * @return calculated discount from campaign
   */
  public Double getCampaignDiscounts() {
    return campaignDiscount;
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

    System.out.println("Total Amount  :\t" + numberFormat.format(getTotalAmountAfterDiscounts()));
    System.out.println("Delivery Cost :\t" + numberFormat.format(getDeliveryCost()));

  }

  public List<Order> getCartOrders() {
    return cartOrders;
  }
}
