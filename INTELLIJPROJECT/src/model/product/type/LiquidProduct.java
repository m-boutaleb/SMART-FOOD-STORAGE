package model.product.type;

import model.product.ProductFreezable;
import model.product.ProductFreshable;
import model.product.Product;

public class LiquidProduct extends Product implements ProductFreezable, ProductFreshable {
    private final double freezableTemperature;
    private final double freshableTemperature;

    public LiquidProduct(final long barCode, final String description,
                            final double weight, final double freezableTemperature, final int quantity
            , final double freshableTemperature) {
        super(barCode, description, weight, quantity);
        this.freezableTemperature=freezableTemperature;
        this.freshableTemperature=freshableTemperature;
    }

    @Override
    public double getFreezableTemperature() {
        return freezableTemperature;
    }

    @Override
    public double getFreshTemperature() {
        return freshableTemperature;
    }

    @Override
    public String getProductType() {
        return LiquidProduct.class.getName();
    }
}
