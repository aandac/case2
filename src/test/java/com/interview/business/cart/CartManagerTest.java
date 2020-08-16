package com.interview.business.cart;

import com.interview.model.Category;
import com.interview.model.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CartManagerTest {

    private CartManager cartManager;
    private Product orange;
    private Product apple;
    private Product almond;

    @Before
    public void setup() {
        cartManager = new CartManagerImpl();
        Category food = new Category("food");
        orange = new Product("Orange", 10.0, food);
        apple = new Product("apple", 8.0, food);
        almond = new Product("almond", 18.0, food);
    }

    @Test
    public void total_cart() {
        Assert.assertEquals(0, cartManager.getCartItems().size());
    }

    @Test
    public void add_cart() {
        Category food = new Category("food");
        Product orange = new Product("Orange", 10.0, food);
        cartManager.addItem(orange, 1);
        Assert.assertEquals(1, cartManager.getCartItems().size());
    }

    @Test
    public void add_multiple_cart() {
        Category food = new Category("food");
        Product orange = new Product("Orange", 10.0, food);
        Product apple = new Product("apple", 8.0, food);
        Product almond = new Product("almond", 18.0, food);
        cartManager.addItem(orange, 1);
        cartManager.addItem(apple, 1);
        Assert.assertEquals(2, cartManager.getCartItems().size());

        cartManager.addItem(apple, 1);
        Assert.assertEquals(2, cartManager.getCartItems().size());

        cartManager.addItem(orange, 1);
        Assert.assertEquals(2, cartManager.getCartItems().size());

        cartManager.addItem(almond, 1);
        Assert.assertEquals(3, cartManager.getCartItems().size());
    }

    @Test
    public void remove_cart() {
        cartManager.addItem(orange, 1);
        cartManager.addItem(apple, 1);
        Assert.assertEquals(2, cartManager.getCartItems().size());

        cartManager.addItem(orange, 1);
        cartManager.removeItem(orange);
        Assert.assertEquals(1, cartManager.getCartItems().size());
    }

    @Test
    public void numberOfDeliveries() {
        cartManager.addItem(orange, 1);
        Assert.assertEquals(1, cartManager.getNumberOfDeliveries());
        cartManager.addItem(apple, 1);
        Assert.assertEquals(1, cartManager.getNumberOfDeliveries());

        Category electronic = new Category("Electronic");
        Product computer = new Product("Apple", 1000.0, electronic);
        cartManager.addItem(computer, 2);
        Assert.assertEquals(2, cartManager.getNumberOfDeliveries());
    }

    @Test
    public void numberOfProducts() {
        cartManager.addItem(orange, 3);
        Assert.assertEquals(1, cartManager.getNumberOfProducts());
        cartManager.addItem(apple, 2);
        Assert.assertEquals(2, cartManager.getNumberOfProducts());
    }
}
