package model;

import model.product.Product;

public class SolidProduct extends Product {
    public SolidProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
