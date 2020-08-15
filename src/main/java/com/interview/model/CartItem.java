package com.interview.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItem {

    private Product product;
    private Integer quantity;

}
