package model.product.type;

import model.product.Product;
import model.product.ProductCellarable;
import model.product.ProductPantriable;

public class MultiUseProduct extends Product implements ProductCellarable, ProductPantriable {
    public MultiUseProduct(final long barCode, final String description, final double weight, final int quantity) {
        super(barCode, description, weight, quantity);
    }

    @Override
    public String getProductType() {
        return MultiUseProduct.class.getName();
    }
}
