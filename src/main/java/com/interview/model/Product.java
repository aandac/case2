package com.interview.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    private String title;
    private Double price;
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(title, product.title) &&
            Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category);
    }
}
