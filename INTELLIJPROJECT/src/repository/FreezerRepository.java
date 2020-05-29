package repository;

import database.Database;
import model.product.Product;
import model.product.ProductFreezable;
import repository.crudservice.CrudRepository;

import java.util.ArrayList;

public class FreezerRepository implements CrudRepository<ProductFreezable> {
    private static FreezerRepository instance;
    private final ArrayList<ProductFreezable> allProducts;
    private final Database database;
    private double temperature;

    private FreezerRepository(final double initialTemperature){
        database=Database.getInstance();
        this.temperature=initialTemperature;
        allProducts =new ArrayList<>();
    }

    public static FreezerRepository getInstance() {
        return instance==null?(instance=new FreezerRepository(0)):instance;
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
            found.setQuantity(found.getQuantity() + newProduct.getQuantity());
            database.saveFreezeProduct(found, temperature);
            return true;
        }
        database.saveFreezeProduct(newProduct, temperature);
        return allProducts.add((ProductFreezable) newProduct);
    }

    @Override
    public String toString() {
        return "Freezer{" +
                "temperature=" + temperature +
                ", allProducts=" + allProducts +
                '}';
    }
}
