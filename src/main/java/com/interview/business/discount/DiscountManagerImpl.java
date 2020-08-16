package com.interview.business.discount;

import com.interview.model.Campaign;
import com.interview.model.CartItem;
import com.interview.model.Coupon;

import java.util.List;

public class DiscountManagerImpl implements DiscountManager {

    private Campaign[] campaigns;
    private Coupon coupon;

    @Override
    public void applyCampaigns(Campaign[] campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public void applyCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @Override
    public Double getCouponDiscount(Double cartTotalAmount) {
        double couponDiscount = 0.0;
        if (coupon != null && cartTotalAmount >= coupon.getMinPurchaseAmount()) {
            couponDiscount = coupon.calculateCouponDiscount(cartTotalAmount);
        }

        return couponDiscount;
    }

    @Override
    public Double getCampaignDiscount(List<CartItem> cartItems) {
        double campaignDiscount = 0.0;

        if (campaigns != null) {
            for (Campaign campaign : campaigns) {
                Double calculatedDiscount = campaign.calculateDiscount(cartItems);
                if (campaignDiscount < calculatedDiscount) {
                    campaignDiscount = calculatedDiscount;
                }
            }
        }

        return campaignDiscount;
    }

}
