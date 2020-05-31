package ch.supsi.sfs.backend.service;

import ch.supsi.sfs.backend.model.product.Product;

public interface ProductCategoryService {
    Product productByBarcodeAndSave(final String barcode, final double weight);
    int[] checkCellarStatus();
    int[] checkFridgeStatus();
    int[] checkPantryStatus();
    int[] checkFreezerStatus();
    void setCellarStatus(final double cellarLight);
    void setFridgeStatus(final double temperature);
    void setFreezerStatus(final double temperature);
    void removeProduct(final String barcode);
}
