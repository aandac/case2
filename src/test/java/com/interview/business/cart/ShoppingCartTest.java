package com.interview.business.cart;

import com.interview.business.cart.CartManagerImpl;
import com.interview.business.cart.ShoppingCart;
import com.interview.business.delivery.DeliveryCostManager;
import com.interview.business.delivery.DeliveryCostManagerImpl;
import com.interview.business.discount.DiscountManagerImpl;
import com.interview.business.discount.DiscountType;
import com.interview.business.print.ConsolePrintManager;
import com.interview.model.Campaign;
import com.interview.model.Category;
import com.interview.model.Coupon;
import com.interview.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    private ShoppingCart cart;
    private Product apple;
    private Product almond;
    private Product computer;
    private Campaign campaign1;
    private Campaign campaign2;
    private Campaign campaign3;
    private Coupon coupon;


    @Before
    public void setup() {
        DeliveryCostManager deliveryCostManager = new DeliveryCostManagerImpl(1.0,1.0,2.99);
        ConsolePrintManager printManager = new ConsolePrintManager();
        DiscountManagerImpl discountManager = new DiscountManagerImpl();
        CartManagerImpl cartManager = new CartManagerImpl();
        cart = new ShoppingCart(deliveryCostManager, discountManager, cartManager, printManager);
        Category food = new Category("food");
        Category electronic = new Category("electronic");
        apple = new Product("Apple", 90.0, food);
        almond = new Product("Almond", 150.0, food);
        computer = new Product("Apple", 1000.0, electronic);

        campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
        campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
        campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);

        coupon = new Coupon(100.0, 10.0, DiscountType.Rate);
    }

    @Test
    public void total_empty() {
        Assert.assertEquals(0, Double.compare(0.0, cart.getTotalAmountAfterDiscounts()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
    }

    @Test
    public void total_without_discounts() {
        cart.addItem(almond, 1);
        Assert.assertEquals(0, Double.compare(150.0, cart.getTotalAmountAfterDiscounts()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));

        cart.addItem(almond, 1);
        Assert.assertEquals(0, Double.compare(300.0, cart.getTotalAmountAfterDiscounts()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));

        cart.addItem(computer, 2);
        Assert.assertEquals(0, Double.compare(2300.0, cart.getTotalAmountAfterDiscounts()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));
    }

    @Test
    public void cart_campaign_discounts() {
        cart.addItem(almond, 1);
        cart.applyCampaigns(campaign1);
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(150.0, cart.getTotalAmountAfterDiscounts()));

        cart.addItem(almond, 3);
        cart.applyCampaigns(campaign1, campaign2);
        Assert.assertEquals(0, Double.compare(120.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(480.0, cart.getTotalAmountAfterDiscounts()));

        cart.addItem(computer, 2);
        cart.addItem(almond, 3);
        cart.applyCampaigns(campaign1, campaign2, campaign3);
        Assert.assertEquals(0, Double.compare(525.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(2525.0, cart.getTotalAmountAfterDiscounts()));
    }

    @Test
    public void total_coupon_discounts() {
        cart.addItem(apple, 1);
        cart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(90.0, cart.getTotalAmountAfterDiscounts()));

        cart.addItem(almond, 3);
        cart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(54.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(486.0, cart.getTotalAmountAfterDiscounts()));
    }

    @Test
    public void total_campaign_coupon_discounts() {
        cart.addItem(apple, 3);
        cart.addItem(almond, 1);
        Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(420.0, cart.getTotalAmountAfterDiscounts()));

        cart.applyCampaigns(campaign1, campaign2, campaign3);
        cart.applyCoupon(coupon);
        Assert.assertEquals(0, Double.compare(84.0, cart.getCampaignDiscount()));
        Assert.assertEquals(0, Double.compare(42.0, cart.getCouponDiscount()));
        Assert.assertEquals(0, Double.compare(294, cart.getTotalAmountAfterDiscounts()));
    }

}
