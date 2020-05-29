package service;

import model.product.Product;

public interface ProductService {
    Product productByBarcodeAndSave(long barcode, final double weight);
}
