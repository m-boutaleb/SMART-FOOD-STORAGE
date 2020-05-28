package model.type;

import model.product.Product;
import model.product.ProductCellarable;

public class FermentedProduct extends Product implements ProductCellarable {
    protected FermentedProduct(final long barCode, final String description, final double weight, final String productType) {
        super(barCode, description, weight, productType);
    }
}
