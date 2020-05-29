package model.product.type;

import model.product.Product;
import model.product.ProductCellarable;

public class FermentedProduct extends Product implements ProductCellarable {
    public FermentedProduct(final long barCode, final String description, final double weight, final int quantity) {
        super(barCode, description, weight,quantity);
    }

    @Override
    public String getProductType() {
        return FermentedProduct.class.getName();
    }
}
