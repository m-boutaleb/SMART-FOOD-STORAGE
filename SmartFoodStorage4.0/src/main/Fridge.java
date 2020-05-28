package main;

import java.util.ArrayList;

public class Fridge implements ProductSection {
    private final ProductCategory type= ProductCategory.FRESHPRODUCT;
    private final ArrayList<Product> allProducts;
    private double temperature;
    public Fridge(final Double initialTemperature ){
        this.allProducts =new ArrayList<>();
        this.temperature=initialTemperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    
    public Double getTemperature() {
        return temperature;
    }

    @Override
    public boolean add(final Product product) throws Exception {
        if(!product.getProductCategories().contains(type))
            throw new IllegalArgumentException("CANNOT ADD A NON CELLAR PRODUCT TO A CELLAR!");
        return allProducts.add(product);
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "temperature=" + temperature +
                ", allProducts=" + allProducts +
                '}';
    }
}
