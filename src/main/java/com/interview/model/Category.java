package com.interview.model;

import java.util.Objects;
import lombok.Data;

@Data
public class Category {

    private String title;
    private Category parentCategory;

    public Category(String title) {
        this.title = title;
    }

    public boolean isSameCategory(Category category) {
        if (category.getTitle().equals(this.title)) {
            return true;
        }

        if (parentCategory == null) {
            return false;
        }

        return this.parentCategory.isSameCategory(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category category = (Category) o;
        return Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
