package model.section;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import model.product.ProductCategory;
import model.product.Product;
import model.section.service.ProductSection;

public class Pantry implements ProductSection {
    private final ProductCategory type= ProductCategory.PANTRYPRODUCT;
    private final Set<Product> allProducts;
    public Pantry(){
        this.allProducts =new HashSet<>();
    }

    @Override
    public void printAllElements() {
        if(allProducts==null)
            return;
        allProducts.forEach(System.out::println);
    }
    @Override
    public boolean add(final Product product) throws Exception {
        if(!product.getProductCategories().contains(type))
            throw new IllegalArgumentException("CANNOT ADD A NON CELLAR PRODUCT TO A CELLAR!");
        allProducts.stream().filter(product::equals).findFirst().orElse(null);
        return allProducts.add(product);
    }

    @Override
    public String toString() {
        return "Pantry{" +
                "allProducts=" + allProducts +
                '}';
    }
}
