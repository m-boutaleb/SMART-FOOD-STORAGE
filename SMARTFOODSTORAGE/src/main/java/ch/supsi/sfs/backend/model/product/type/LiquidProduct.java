package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.ProductFreezable;
import ch.supsi.sfs.backend.model.product.ProductFreshable;
import ch.supsi.sfs.backend.model.product.Product;

public class LiquidProduct extends Product implements ProductFreezable, ProductFreshable {
    //temperatura che può mantenere nel freezer
    private final double freezableTemperature;
    //temperature che può mantenere nel frigo
    private final double freshableTemperature;

    public LiquidProduct(final String barCode, final String description,
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
        return this.getClass().getSimpleName();
    }
}
