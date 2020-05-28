package model;

import model.product.Product;

public class DisposableProduct extends Product {
    public DisposableProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
