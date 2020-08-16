package com.interview.business.cart;

import com.interview.business.delivery.DeliveryCostManager;
import com.interview.business.discount.DiscountManager;
import com.interview.business.print.PrintManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartUnitTest {

    private ShoppingCart cart;
    private CartManager cartManager;
    private PrintManager printManager;
    private DiscountManager discountManager;
    private DeliveryCostManager deliveryCostManager;

    @Before
    public void setup() {
        deliveryCostManager = mock(DeliveryCostManager.class);
        discountManager = mock(DiscountManager.class);
        cartManager = mock(CartManager.class);
        printManager = mock(PrintManager.class);
        cart = new ShoppingCart(deliveryCostManager, discountManager, cartManager, printManager);
    }

    @Test
    public void getTotalAmountAfterDiscounts_total_is_zero() {
        when(cartManager.getCartItems()).thenReturn(Collections.emptyList());
        when(cartManager.getCartTotalAmountBeforeDiscount()).thenReturn(10.0);
        when(discountManager.getCampaignDiscount(cartManager.getCartItems())).thenReturn(3.0);
        when(discountManager.getCouponDiscount(10.0)).thenReturn(7.0);

        Assert.assertEquals(0, Double.compare(0.0, cart.getTotalAmountAfterDiscounts()));
    }

    @Test
    public void getTotalAmountAfterDiscounts_total_is_less_than_zero() {
        when(cartManager.getCartItems()).thenReturn(Collections.emptyList());
        when(cartManager.getCartTotalAmountBeforeDiscount()).thenReturn(10.0);
        when(discountManager.getCampaignDiscount(cartManager.getCartItems())).thenReturn(3.0);
        when(discountManager.getCouponDiscount(10.0)).thenReturn(8.0);

        Assert.assertEquals(0, Double.compare(0.0, cart.getTotalAmountAfterDiscounts()));
    }
}
