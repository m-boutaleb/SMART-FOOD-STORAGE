package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductFreshable;
import ch.supsi.sfs.backend.model.product.ProductPantriable;

public class AboveZeroProduct extends Product implements ProductPantriable, ProductFreshable {
    private final double freshTemperature;

    public AboveZeroProduct(final String barCode, final String description, final double weight, final int quantity, final double freshTemperature) {
        super(barCode, description, weight, quantity);
        this.freshTemperature = freshTemperature;
    }


    @Override
    public double getFreshTemperature() {
        return freshTemperature;
    }

    @Override
    public String getProductType() {
        return AboveZeroProduct.class.getSimpleName();
    }
}
