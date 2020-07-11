package ch.supsi.sfs.backend.controller;

import ch.supsi.sfs.backend.service.ProductCategoryService;
import ch.supsi.sfs.backend.service.implementation.ProductCategoryServiceImpl;

/**
 * Classe usata dal frontend per interfacciarsi con il backend
 */
public class StorageController {
    private ProductCategoryService productCategoryService;
    private static StorageController instance;

    private StorageController(){
        productCategoryService = ProductCategoryServiceImpl.getInstance();
    }

    public static StorageController getInstance() {
        return instance==null?(instance=new StorageController()):instance;
    }

    public void saveProduct(final String barcode, final double weight){
        productCategoryService.productByBarcodeAndSave(barcode, weight);
    }

    public int[] checkCellarStatus(){
        return productCategoryService.checkCellarStatus();
    }
    public int[] checkFridgeStatus(){
        return productCategoryService.checkFridgeStatus();
    }
    public int[] checkFreezerStatus(){
        return productCategoryService.checkFreezerStatus();
    }
    public int[] checkPantryStatus(){
        return productCategoryService.checkPantryStatus();
    }

    public void setCellarLight(final double cellarLight) {
        productCategoryService.setCellarStatus(cellarLight);
    }

    public void setFridgeTemp(final double temperature) {
        productCategoryService.setFridgeStatus(temperature);
    }

    public void setFreezerTemp(final double temperature) {
        productCategoryService.setFreezerStatus(temperature);
    }

    public void removeElement(final String barcode) {
        productCategoryService.removeProduct(barcode);
    }
}
