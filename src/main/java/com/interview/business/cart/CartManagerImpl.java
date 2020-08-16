package com.interview.business.cart;

import com.interview.model.CartItem;
import com.interview.model.Category;
import com.interview.model.Product;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CartManagerImpl implements CartManager {
    final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));

    private final List<CartItem> cartItems;

    public CartManagerImpl() {
        this.cartItems = new ArrayList<>();
    }

    public Double getCartTotalAmountBeforeDiscount() {
        return cartItems
                .stream()
                .mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
                .sum();
    }

    @Override
    public void addItem(Product product, Integer quantity) {
        Optional<CartItem> existItem = this.cartItems.stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findAny();
        if (existItem.isPresent()) {
            CartItem item = existItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            this.cartItems.add(new CartItem(product, quantity));
        }
    }

    @Override
    public void removeItem(Product product) {
        this.cartItems.stream()
                .filter(ci -> ci.getProduct().equals(product))
                .findAny()
                .ifPresent(this.cartItems::remove);
    }

    @Override
    public List<CartItem> getCartItems() {
        return cartItems;
    }


    @Override
    public int getNumberOfDeliveries() {
        // Number of deliveries is calculated by the number of distinct categories in the cart
        return cartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getCategory()))
                .size();
    }

    @Override
    public int getNumberOfProducts() {
        // Number of products is the number of different products in the cart
        return cartItems.stream()
                .collect(Collectors.groupingBy(CartItem::getProduct))
                .size();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Map<Category, List<CartItem>> groupedProducts = cartItems.stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getCategory()));

        builder.append(String.format("%-12s \t%-12s \t%12s \t%12s \t%12s\n", "Category", "Product Name",
                "Quantity", "Unit Price", "Total Price"));

        for (Category category : groupedProducts.keySet()) {
            List<CartItem> cartItems = groupedProducts.get(category);
            for (CartItem cartItem : cartItems) {
                Product product = cartItem.getProduct();
                builder.append(String.format("%-12s \t%-12s \t%12s \t%12s \t%12s\n", category.getTitle(), product.getTitle(),
                        cartItem.getQuantity(), numberFormat
                                .format(product.getPrice()), numberFormat
                                .format(cartItem.getQuantity() * product.getPrice())));
            }
        }
        return builder.toString();
    }
}
