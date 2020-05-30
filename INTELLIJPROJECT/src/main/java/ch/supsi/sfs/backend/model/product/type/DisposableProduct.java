package ch.supsi.sfs.backend.model.product.type;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductPantriable;

public class DisposableProduct extends Product implements ProductPantriable {
    public DisposableProduct(final String barCode, final String description, final double weight, final int quantity) {
        super(barCode, description, weight, quantity);
    }

    @Override
    public String getProductType() {
        return DisposableProduct.class.getSimpleName();
    }
}
