package model;

import model.product.Product;

public class LiquidProduct extends Product {

    public LiquidProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
