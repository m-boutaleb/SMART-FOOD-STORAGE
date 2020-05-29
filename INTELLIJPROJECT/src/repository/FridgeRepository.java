package repository;

import database.Database;
import model.product.Product;
import model.product.ProductFreshable;
import repository.crudservice.CrudRepository;

import java.util.HashSet;
import java.util.Set;

public class FridgeRepository implements CrudRepository<ProductFreshable> {
    private static FridgeRepository instance;
    private final Set<ProductFreshable> allProducts;
    private double temperature;
    private final Database database;

    private FridgeRepository(final double initialTemperature ){
        database=Database.getInstance();
        this.allProducts =new HashSet<>();
        this.temperature=initialTemperature;
    }

    public static FridgeRepository getInstance() {
        return instance==null?(instance=new FridgeRepository(0)):instance;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    public double getTemperature() {
        return temperature;
    }

    @Override
    public void printAllElements() {
        allProducts.forEach(System.out::println);
    }

    @Override
    public boolean add(final ProductFreshable product){
        Product newProduct=(Product)product;

        if(newProduct==null)
            throw new IllegalArgumentException("PRODUCT CANNOT BE NULL");

        Product found=null;

        if(allProducts.contains(product))
            found=allProducts.stream().parallel().map(i->(Product)i)
                    .filter(newProduct::equals).findFirst().orElse(null);

        if(found!=null) {
            found.setQuantity(found.getQuantity() + newProduct.getQuantity());
            database.saveFridgeProduct(found, temperature);
            return true;
        }
        database.saveFridgeProduct(newProduct, temperature);
        return allProducts.add((ProductFreshable) newProduct);
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "temperature=" + temperature +
                ", allProducts=" + allProducts +
                '}';
    }
}
