package model.type;

import model.product.ProductFreezable;
import model.product.ProductFreshable;
import model.product.Product;

public class LiquidProduct extends Product implements ProductFreezable, ProductFreshable {
    private final double freezableTemperature;
    private final double freshableTemperature;

    protected LiquidProduct(final long barCode, final String description,
                            final double weight, final String productType, final double freezableTemperature
            , final double freshableTemperature) {
        super(barCode, description, weight, productType);
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
}
