package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductFreezable;

public class BelowZeroProduct extends Product implements ProductFreezable {
    //temperature ottimale che il prodotto deve mantenere
    private final double freezableTemperature;

    public BelowZeroProduct(final String barCode, final String description, final double weight, final int quantity, final double freezableTemperature) {
        super(barCode, description, weight, quantity);
        this.freezableTemperature=freezableTemperature;
    }


    @Override
    public double getFreezableTemperature() {
        return freezableTemperature;
    }

    @Override
    public String getProductType() {
        return BelowZeroProduct.class.getSimpleName();
    }
}
