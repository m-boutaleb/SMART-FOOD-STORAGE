package model.type;

import model.product.Product;
import model.product.ProductFreezable;

public class AboveZeroProduct extends Product implements ProductFreezable {


    protected AboveZeroProduct(final long barCode, final String description, final double weight, final String productType) {
        super(barCode, description, weight, productType);
    }

    @Override
    public double getFreezableTemperature() {
        return 0;
    }
}
