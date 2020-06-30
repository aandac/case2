package com.interview.business;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.interview.business.DeliveryCostCalculator;
import com.interview.business.ShoppingCart;
import com.interview.model.Category;
import com.interview.model.Order;
import com.interview.model.Product;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryCostCalculatorTest {

  private ShoppingCart cart;

  private DeliveryCostCalculator costCalculator;
  private final double fixedCost = 2.99;
  private Product apple;
  private Product computer;
  private Product almond;

  @Before
  public void setUp() {
    cart = mock(ShoppingCart.class);
    costCalculator = new DeliveryCostCalculator(1.0, 1.0, fixedCost);
    Category food = new Category("food");
    Category electronic = new Category("electronic");
    apple = new Product("Apple", 100.0, food);
    almond = new Product("Almond", 150.0, food);
    computer = new Product("Apple", 1000.0, electronic);
  }
  @Test
  public void empty_cart() {
    when(cart.getCartOrders()).thenReturn(Collections.emptyList());
    Assert.assertEquals(0, Double.compare(costCalculator.calculateFor(cart), fixedCost));
  }

  @Test
  public void cost_single_product() {
    List<Order> singleCategory = new ArrayList<>();
    singleCategory.add(new Order(apple, 1));
    singleCategory.add(new Order(apple, 1));
    when(cart.getCartOrders()).thenReturn(singleCategory);
    Assert.assertEquals(0, Double.compare(costCalculator.calculateFor(cart), 4.99));
  }

  @Test
  public void cost_multiple_product() {
    List<Order> singleCategory = new ArrayList<>();
    singleCategory.add(new Order(apple, 1));
    singleCategory.add(new Order(almond, 1));
    when(cart.getCartOrders()).thenReturn(singleCategory);
    Assert.assertEquals(0, Double.compare(costCalculator.calculateFor(cart), 5.99));
  }

  @Test
  public void cost_multiple_category() {
    List<Order> multipleCategoryOrder = new ArrayList<>();
    multipleCategoryOrder.add(new Order(apple, 1));
    multipleCategoryOrder.add(new Order(computer, 1));
    when(cart.getCartOrders()).thenReturn(multipleCategoryOrder);
    Assert.assertEquals(0, Double.compare(costCalculator.calculateFor(cart), 6.99));
  }




}
