package com.interview.business;

import static org.mockito.Mockito.mock;

import com.interview.model.Campaign;
import com.interview.model.Category;
import com.interview.model.Coupon;
import com.interview.model.DiscountType;
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
    DeliveryCostCalculator costCalculator = mock(DeliveryCostCalculator.class);
    cart = new ShoppingCart(costCalculator);
    Category food = new Category("food");
    Category electronic = new Category("electronic");
    apple = new Product("Apple", 90.0, food);
    almond = new Product("Almond", 150.0, food);
    computer = new Product("Apple", 1000.0, electronic);
    campaign1 = new Campaign(food, 20.0, 3, DiscountType.Rate);
    campaign2 = new Campaign(food, 50.0, 5, DiscountType.Rate);
    campaign3 = new Campaign(food, 5.0, 5, DiscountType.Amount);
    coupon = new Coupon(100, 10.0, DiscountType.Rate);
  }

  @Test
  public void total_empty() {
    Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getTotalAmountAfterDiscounts()));
  }

  @Test
  public void total_without_discounts() {
    cart.addItem(almond, 1);
    Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(150.0, cart.getTotalAmountAfterDiscounts()));

    cart.addItem(almond, 1);
    Assert.assertEquals(0, Double.compare(300.0, cart.getTotalAmountAfterDiscounts()));

    cart.addItem(computer, 2);
    Assert.assertEquals(0, Double.compare(2300.0, cart.getTotalAmountAfterDiscounts()));
  }

  @Test
  public void total_campaign_discounts() {
    cart.addItem(almond, 1);
    cart.applyDiscounts(campaign1);
    Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(150.0, cart.getTotalAmountAfterDiscounts()));

    cart.addItem(almond, 3);
    cart.applyDiscounts(campaign1, campaign2);
    Assert.assertEquals(0, Double.compare(120.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(480.0, cart.getTotalAmountAfterDiscounts()));

    cart.addItem(computer, 2);
    cart.addItem(almond, 3);
    cart.applyDiscounts(campaign1, campaign2, campaign3);
    Assert.assertEquals(0, Double.compare(525.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(2525.0, cart.getTotalAmountAfterDiscounts()));
  }

  @Test
  public void total_coupon_discounts() {
    cart.addItem(apple, 1);
    cart.applyCoupon(coupon);
    Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(90.0, cart.getTotalAmountAfterDiscounts()));

    cart.addItem(almond, 3);
    cart.applyCoupon(coupon);
    Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(54.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(486.0, cart.getTotalAmountAfterDiscounts()));
  }

  @Test
  public void total_campaign_coupon_discounts() {
    cart.addItem(apple, 3);
    cart.addItem(almond, 1);
    Assert.assertEquals(0, Double.compare(0.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(420.0, cart.getTotalAmountAfterDiscounts()));

    cart.applyDiscounts(campaign1, campaign2, campaign3);
    cart.applyCoupon(coupon);
    Assert.assertEquals(0, Double.compare(84.0, cart.getCampaignDiscounts()));
    Assert.assertEquals(0, Double.compare(33.6, cart.getCouponDiscounts()));
    Assert.assertEquals(0, Double.compare(302.4, cart.getTotalAmountAfterDiscounts()));
  }

  @Test
  public void cart_items(){
    Assert.assertEquals(0, cart.getCartOrders().size());
    cart.addItem(apple, 3);
    Assert.assertEquals(1, cart.getCartOrders().size());
  }

}
