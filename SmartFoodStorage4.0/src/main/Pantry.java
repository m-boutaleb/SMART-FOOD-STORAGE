package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Pantry implements ProductSection {
    private final ProductCategory type= ProductCategory.PANTRYPRODUCT;
    private final Set<Product> allProducts;
    
    public Pantry(){
        this.allProducts =new HashSet<>();
    }

    @Override
    public boolean add(final Product product) throws Exception {
        if(!product.getProductCategories().contains(type))
            throw new IllegalArgumentException("CANNOT ADD A NON CELLAR PRODUCT TO A CELLAR!");
        final Product prev=allProducts.stream().filter(product::equals).findFirst().orElse(null);
        if(prev==null)
            return allProducts.add(prev);
        product.setQuantity(prev==null?product.getQuantity():(product.getQuantity()+prev.getQuantity()));
        return true;
    }

    @Override
    public String toString() {
        return "Pantry{" +
                "allProducts=" + allProducts +
                '}';
    }
}
