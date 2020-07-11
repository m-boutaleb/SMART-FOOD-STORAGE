package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductFreshable;
import ch.supsi.sfs.backend.model.product.ProductPantriable;

public class SolidProduct extends Product implements ProductFreshable, ProductPantriable {
    //temparature ottimale che deve mantenere in frigo
    private final double freshableTemperature;

    public SolidProduct(final String barCode, final String description, final double weight, final int quantity, final double freshableTemperature) {
        super(barCode, description, weight, quantity);
        this.freshableTemperature=freshableTemperature;
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
