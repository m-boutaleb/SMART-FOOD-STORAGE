package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductCellarable;

public class FermentedProduct extends Product implements ProductCellarable {
    public FermentedProduct(final String barCode, final String description, final double weight, final int quantity) {
        super(barCode, description, weight,quantity);
    }

    @Override
    public String getProductType() {
        return this.getClass().getSimpleName();
    }
}
