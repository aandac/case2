package com.interview.business.cart;

import com.interview.business.delivery.DeliveryCostManager;
import com.interview.business.discount.DiscountManager;
import com.interview.business.print.PrintManager;
import com.interview.model.Campaign;
import com.interview.model.CartItem;
import com.interview.model.Coupon;
import com.interview.model.Product;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShoppingCart {

    final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
    private final CartManager cartManager;
    private final DeliveryCostManager deliveryCostManager;
    private final DiscountManager discountManager;
    private final PrintManager printManager;

    public ShoppingCart(
            DeliveryCostManager deliveryCostManager,
            DiscountManager discountManager,
            CartManager cartManager,
            PrintManager printManager) {
        this.deliveryCostManager = deliveryCostManager;
        this.discountManager = discountManager;
        this.cartManager = cartManager;
        this.printManager = printManager;
    }

    public void applyCampaigns(Campaign... campaigns) {
        discountManager.applyCampaigns(campaigns);
    }

    public void applyCoupon(Coupon coupon) {
        discountManager.applyCoupon(coupon);
    }

    public void addItem(Product product, Integer quantity) {
        cartManager.addItem(product, quantity);
    }

    public void removeItem(Product product) {
        cartManager.removeItem(product);
    }

    public Double getDeliveryCost() {
        return deliveryCostManager.getDeliveryCost(cartManager.getNumberOfDeliveries(), cartManager.getNumberOfProducts());
    }

    public Double getCouponDiscount() {
        return discountManager.getCouponDiscount(cartManager.getCartTotalAmountBeforeDiscount());
    }

    public Double getCampaignDiscount() {
        return discountManager.getCampaignDiscount(cartManager.getCartItems());
    }

    public Double getTotalAmountAfterDiscounts() {
        List<CartItem> items = cartManager.getCartItems();
        Double totalAmountBeforeDiscount = cartManager.getCartTotalAmountBeforeDiscount();
        Double campaignDiscount = discountManager.getCampaignDiscount(items);
        Double couponDiscount = discountManager.getCouponDiscount(totalAmountBeforeDiscount);

        double total = totalAmountBeforeDiscount - campaignDiscount - couponDiscount;
        return Math.max(total, 0.0);
    }

    public void print() {
        printManager.print(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(cartManager.toString());
        Double campaignDiscount = discountManager.getCampaignDiscount(cartManager.getCartItems());
        Double couponDiscount = discountManager.getCouponDiscount(cartManager.getCartTotalAmountBeforeDiscount());
        builder.append("\t\t\t\t\t%77").append(numberFormat.format(campaignDiscount)).append(" (Campaign)\n");
        builder.append("\t\t\t\t\t%75").append(numberFormat.format(couponDiscount)).append(" (Coupon)\n");
        builder.append("Total Amount  :\t").append(numberFormat.format(getTotalAmountAfterDiscounts()));
        builder.append("Delivery Cost :\t").append(numberFormat.format(getDeliveryCost()));
        return builder.toString();
    }

}
