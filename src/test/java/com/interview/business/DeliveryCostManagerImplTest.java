package com.interview.business;

import com.interview.business.delivery.DeliveryCostManagerImpl;
import com.interview.model.CartItem;
import com.interview.model.Category;
import com.interview.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

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
                Double.compare(costCalculator.getDeliveryCost(0, 0), 0));
    }

    @Test
    public void cost_single_product() {
        Assert.assertEquals(0, Double.compare(costCalculator.getDeliveryCost(1, 1), 4.99));
    }

    @Test
    public void cost_multiple_product() {
        Assert.assertEquals(0, Double.compare(costCalculator.getDeliveryCost(1, 2), 5.99));
    }

    @Test
    public void cost_multiple_category() {
        Assert.assertEquals(0,
                Double.compare(costCalculator.getDeliveryCost(2, 2), 6.99));
    }
}
