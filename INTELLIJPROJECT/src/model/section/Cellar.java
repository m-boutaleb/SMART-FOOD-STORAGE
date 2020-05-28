package model.section;

import model.database.Database;
import model.product.Product;
import model.product.ProductCellarable;
import model.section.service.ProductSection;
import java.util.HashSet;
import java.util.Set;

public class Cellar implements ProductSection<ProductCellarable> {
    private double light;
    private final Set<ProductCellarable> allProducts;
    private final Database database;
    private final int NUMBER_OF_TYPES=2;

    public Cellar(final double initialLight){
        database=Database.getInstance();
        this.allProducts =new HashSet<>();
        this.light=initialLight;
    }

    public double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
    }


    @Override
    public void printAllElements() {
        new HashSet<>(allProducts);
    }


    @Override
    public boolean add(final ProductCellarable product) {
        Product newProduct=(Product)product;

        if(newProduct==null)
            throw new IllegalArgumentException("PRODUCT CANNOT BE NULL");

        Product found=null;

        if(allProducts.contains(product))
            found=allProducts.stream().parallel().map(i->(Product)i)
                    .filter(newProduct::equals).findFirst().orElse(null);

        if(found!=null) {
            found.setQuantity(found.getQuantity() + 1);
            database.saveCellarProduct(found, light);
            return true;
        }
        database.saveCellarProduct(newProduct, light);
        return allProducts.add((ProductCellarable) newProduct);
    }

    @Override
    public ProductCellarable getRandomType() {
        return
    }

    @Override
    public String toString() {
        return "Cellar{" +
                "light=" + light +
                ", allProducts=" + allProducts +
                '}';
    }
}
