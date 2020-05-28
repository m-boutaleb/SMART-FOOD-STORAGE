package model.section;

import model.product.ProductCategory;
import model.product.Product;
import model.section.service.ProductSection;

import java.util.ArrayList;
import java.util.HashSet;

public class Cellar implements ProductSection {
    private final ProductCategory type= ProductCategory.CELLARPRODUCT;
    private Double light;
    private final ArrayList<Product> allProducts;

    public Cellar(final Double initialLight){
        this.allProducts =new ArrayList<>();
        this.light=initialLight;
    }

    public Double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
    }


    @Override
    public void printAllElements() {
        if(allProducts==null)
            return;
        new HashSet<>(allProducts);
    }
    @Override
    public boolean add(final Product product) throws Exception {
        if(!product.getProductCategories().contains(type))
            throw new IllegalArgumentException("CANNOT ADD A NON CELLAR PRODUCT TO A CELLAR!");
        return allProducts.add(product);
    }

    @Override
    public String toString() {
        return "Cellar{" +
                "light=" + light +
                ", allProducts=" + allProducts +
                '}';
    }
}
