package model;

import model.product.Product;

public class AboveZeroProduct extends Product {
    public AboveZeroProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
