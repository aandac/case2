package com.interview.business;

import com.interview.business.cart.CartOperationsImpl;
import com.interview.model.Category;
import com.interview.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

  private CartOperationsImpl cart;
  private Product apple;
  private Product almond;
  private Product computer;


  @Before
  public void setup() {
    cart = new CartOperationsImpl();
    Category food = new Category("food");
    Category electronic = new Category("electronic");
    apple = new Product("Apple", 90.0, food);
    almond = new Product("Almond", 150.0, food);
    computer = new Product("Apple", 1000.0, electronic);
  }

  @Test
  public void total_empty() {
    Assert.assertEquals(0, Double.compare(0.0, cart.getCartTotalAmountBeforeDiscount()));
    Assert.assertEquals(0, Double.compare(0.0, cart.getCartItems().size()));
  }

}
