package model.product.type;

import model.product.Product;
import model.product.ProductFreshable;
import model.product.ProductPantriable;

public class SolidProduct extends Product implements ProductFreshable, ProductPantriable {
    private final double freshableTemperature;

    public SolidProduct(final long barCode, final String description, final double weight,final int quantity, final double freshableTemperature) {
        super(barCode, description, weight, quantity);
        this.freshableTemperature=freshableTemperature;
    }

    @Override
    public double getFreshTemperature() {
        return freshableTemperature;
    }

    @Override
    public String getProductType() {
        return SolidProduct.class.getName();
    }
}
