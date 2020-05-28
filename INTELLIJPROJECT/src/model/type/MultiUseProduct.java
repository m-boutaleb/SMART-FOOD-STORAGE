package model.type;

import model.product.Product;
import model.product.ProductCellarable;
import model.product.ProductPantriable;

public class MultiUseProduct extends Product implements ProductCellarable, ProductPantriable {
    protected MultiUseProduct(final long barCode, final String description, final double weight, final String productType) {
        super(barCode, description, weight, productType);
    }
}
