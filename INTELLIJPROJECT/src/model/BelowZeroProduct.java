package model;

import model.product.Product;

public class BelowZeroProduct extends Product {

    public BelowZeroProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
