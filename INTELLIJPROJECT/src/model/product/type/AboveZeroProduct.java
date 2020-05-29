package model.product.type;

import model.product.Product;
import model.product.ProductFreezable;

public class AboveZeroProduct extends Product implements ProductFreezable {
    private final double freezableTemperature;


    public AboveZeroProduct(final long barCode, final String description, final double weight, final int quantity, final double freezableTemperature) {
        super(barCode, description, weight, quantity);
        this.freezableTemperature=freezableTemperature;
    }


    @Override
    public double getFreezableTemperature() {
        return freezableTemperature;
    }

    @Override
    public String getProductType() {
        return AboveZeroProduct.class.getName();
    }
}
