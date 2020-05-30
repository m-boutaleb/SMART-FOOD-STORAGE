package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductFreshable;
import ch.supsi.sfs.backend.model.product.ProductPantriable;

public class BelowZeroProduct extends Product implements ProductPantriable, ProductFreshable {
    private final double freshTemperature;

    public BelowZeroProduct(final String barCode, final String description, final double weight, final int quantity, final double freshTemperature) {
        super(barCode, description, weight, quantity);
        this.freshTemperature = freshTemperature;
    }


    @Override
    public double getFreshTemperature() {
        return freshTemperature;
    }

    @Override
    public String getProductType() {
        return BelowZeroProduct.class.getSimpleName();
    }
}
