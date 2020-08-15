package com.interview.business;

import com.interview.business.delivery.DeliveryCostManagerImpl;
import com.interview.model.CartItem;
import com.interview.model.Category;
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
public class DeliveryCostManagerImplTest {

  private DeliveryCostManagerImpl costCalculator;
  private Product apple;
  private Product computer;
  private Product almond;

  @Before
  public void setUp() {
    double fixedCost = 2.99;
    costCalculator = new DeliveryCostManagerImpl(1.0, 1.0, fixedCost);
    Category food = new Category("food");
    Category electronic = new Category("electronic");
    apple = new Product("Apple", 100.0, food);
    almond = new Product("Almond", 150.0, food);
    computer = new Product("Apple", 1000.0, electronic);
  }

  @Test
  public void empty_cart() {
    Assert.assertEquals(0,
        Double.compare(costCalculator.getDeliveryCost(Collections.emptyList()), 0));
  }

  @Test
  public void cost_single_product() {
    List<CartItem> singleCategory = new ArrayList<>();
    singleCategory.add(new CartItem(apple, 1));
    singleCategory.add(new CartItem(apple, 1));
    Assert.assertEquals(0, Double.compare(costCalculator.getDeliveryCost(singleCategory), 4.99));
  }

  @Test
  public void cost_multiple_product() {
    List<CartItem> singleCategory = new ArrayList<>();
    singleCategory.add(new CartItem(apple, 1));
    singleCategory.add(new CartItem(almond, 1));
    Assert.assertEquals(0, Double.compare(costCalculator.getDeliveryCost(singleCategory), 5.99));
  }

  @Test
  public void cost_multiple_category() {
    List<CartItem> multipleCategoryOrder = new ArrayList<>();
    multipleCategoryOrder.add(new CartItem(apple, 1));
    multipleCategoryOrder.add(new CartItem(computer, 1));
    Assert.assertEquals(0,
        Double.compare(costCalculator.getDeliveryCost(multipleCategoryOrder), 6.99));
  }
}
