package model.type;

import model.product.Product;
import model.product.ProductFreshable;
import model.product.ProductPantriable;

public class SolidProduct extends Product implements ProductFreshable, ProductPantriable {
    private final double freshableTemperature;

    protected SolidProduct(final long barCode, final String description, final double weight, final String productType, final double freshableTemperature) {
        super(barCode, description, weight, productType);
        this.freshableTemperature=freshableTemperature;
    }

    @Override
    public double getFreshTemperature() {
        return freshableTemperature;
    }
}
