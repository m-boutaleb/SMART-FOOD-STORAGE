package model.type;

import model.product.Product;
import model.product.ProductFreshable;
import model.product.ProductPantriable;

public class BelowZeroProduct extends Product implements ProductPantriable, ProductFreshable {
    private final double freshTemperature;

    protected BelowZeroProduct(final long barCode, final String description, final double weight, final String productType, double freshTemperature) {
        super(barCode, description, weight, productType);
        this.freshTemperature = freshTemperature;
    }


    @Override
    public double getFreshTemperature() {
        return freshTemperature;
    }
}
