package model.product.type;

import model.product.Product;
import model.product.ProductFreshable;
import model.product.ProductPantriable;

public class BelowZeroProduct extends Product implements ProductPantriable, ProductFreshable {
    private final double freshTemperature;

    public BelowZeroProduct(final long barCode, final String description, final double weight, final int quantity,final double freshTemperature) {
        super(barCode, description, weight, quantity);
        this.freshTemperature = freshTemperature;
    }


    @Override
    public double getFreshTemperature() {
        return freshTemperature;
    }

    @Override
    public String getProductType() {
        return BelowZeroProduct.class.getName();
    }
}
