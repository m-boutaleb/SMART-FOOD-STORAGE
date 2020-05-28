package model.section;

import model.database.Database;
import model.product.Product;
import model.product.ProductFreezable;
import model.section.service.ProductSection;

import java.util.ArrayList;

public class Freezer implements ProductSection<ProductFreezable> {
    private final ArrayList<ProductFreezable> allProducts;
    private final Database database;
    private double temperature;

    public Freezer(final double initialTemperature){
        database=Database.getInstance();
        this.temperature=initialTemperature;
        allProducts =new ArrayList<>();
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getTemperature() {
        return temperature;
    }

    @Override
    public void printAllElements() {
        allProducts.forEach(System.out::println);
    }

    @Override
    public boolean add(final ProductFreezable product){
        Product newProduct=(Product)product;

        if(newProduct==null)
            throw new IllegalArgumentException("PRODUCT CANNOT BE NULL");

        Product found=null;

        if(allProducts.contains(product))
            found=allProducts.stream().parallel().map(i->(Product)i)
                    .filter(newProduct::equals).findFirst().orElse(null);

        if(found!=null) {
            found.setQuantity(found.getQuantity() + 1);
            database.saveFreezeProduct(found, temperature);
            return true;
        }
        database.saveFreezeProduct(newProduct, temperature);
        return allProducts.add((ProductFreezable) newProduct);
    }

    @Override
    public ProductFreezable getRandomType() {
        return null;
    }

    @Override
    public String toString() {
        return "Freezer{" +
                "temperature=" + temperature +
                ", allProducts=" + allProducts +
                '}';
    }
}
