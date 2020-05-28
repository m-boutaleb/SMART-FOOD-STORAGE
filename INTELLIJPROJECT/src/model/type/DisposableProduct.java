package model.type;

import model.product.Product;
import model.product.ProductPantriable;

public class DisposableProduct extends Product implements ProductPantriable {
    protected DisposableProduct(final long barCode, final String description, final double weight, final String productType) {
        super(barCode, description, weight, productType);
    }
}
