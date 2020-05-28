package model;

import model.product.Product;

public class MultiUseProduct extends Product {
    public MultiUseProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
