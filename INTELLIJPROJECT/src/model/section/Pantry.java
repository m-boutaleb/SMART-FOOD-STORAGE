package model.section;

import java.util.HashSet;
import java.util.Set;

import model.database.Database;
import model.product.Product;
import model.product.ProductFreezable;
import model.product.ProductPantriable;
import model.section.service.ProductSection;

public class Pantry implements ProductSection<ProductPantriable> {
    private final Set<ProductPantriable> allProducts;
    private final Database database;

    public Pantry(){
        this.database = Database.getInstance();
        this.allProducts =new HashSet<>();
    }

    @Override
    public void printAllElements() {
        allProducts.forEach(System.out::println);
    }

    @Override
    public boolean add(final ProductPantriable product){
        Product newProduct=(Product)product;

        if(newProduct==null)
            throw new IllegalArgumentException("PRODUCT CANNOT BE NULL");

        Product found=null;

        if(allProducts.contains(product))
            found=allProducts.stream().parallel().map(i->(Product)i)
                    .filter(newProduct::equals).findFirst().orElse(null);

        if(found!=null) {
            found.setQuantity(found.getQuantity() + 1);
            database.savePantryProduct(found);
            return true;
        }
        database.savePantryProduct(newProduct);
        return allProducts.add((ProductPantriable) newProduct);
    }

    @Override
    public ProductPantriable getRandomType() {
        return null;
    }

    @Override
    public String toString() {
        return "Pantry{" +
                "allProducts=" + allProducts +
                '}';
    }
}
