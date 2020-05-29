package model.product.type;

import model.product.Product;
import model.product.ProductPantriable;

public class DisposableProduct extends Product implements ProductPantriable {
    public DisposableProduct(final long barCode, final String description, final double weight, final int quantity) {
        super(barCode, description, weight, quantity);
    }

    @Override
    public String getProductType() {
        return DisposableProduct.class.getName();
    }
}
