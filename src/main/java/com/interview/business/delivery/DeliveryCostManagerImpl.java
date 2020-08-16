package com.interview.business.delivery;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeliveryCostManagerImpl implements DeliveryCostManager {

    private final Double costPerDelivery;
    private final Double costPerProduct;
    private final Double fixedCost;

    @Override
    public Double getDeliveryCost(int numberOfDeliveries, int numberOfProducts) {
        if (numberOfDeliveries == 0 && numberOfProducts == 0) {
            return 0.0;
        }
        return (costPerDelivery * numberOfDeliveries) + (costPerProduct * numberOfProducts) + fixedCost;
    }

}
