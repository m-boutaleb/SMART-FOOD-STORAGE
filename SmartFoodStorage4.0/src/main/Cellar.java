package main;


import java.util.HashSet;
import java.util.Set;

public class Cellar implements ProductSection {
    private final ProductCategory type= ProductCategory.CELLARPRODUCT;
    private Double light;
    private final Set<Product> allProducts;

    public Cellar(final Double initialLight){
        this.allProducts =new HashSet<>();
        this.light=initialLight;
    }

    public Double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
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
