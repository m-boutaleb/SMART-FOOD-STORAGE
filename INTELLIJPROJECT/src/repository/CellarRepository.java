package repository;

import database.Database;
import model.product.Product;
import model.product.ProductCellarable;
import repository.crudservice.CrudRepository;
import java.util.HashSet;
import java.util.Set;

public class CellarRepository implements CrudRepository<ProductCellarable> {
    private static CellarRepository instance;
    private double light;
    private final Set<ProductCellarable> allProducts;
    private final Database database;
    private final int NUMBER_OF_TYPES=2;

    private CellarRepository(final double initialLight){
        database=Database.getInstance();
        this.allProducts =new HashSet<>();
        this.light=initialLight;
    }

    public static CellarRepository getInstance() {
        return instance==null?(instance=new CellarRepository(0)):instance;
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
            found.setQuantity(found.getQuantity() + newProduct.getQuantity());
            database.saveCellarProduct(found, light);
            return true;
        }
        database.saveCellarProduct(newProduct, light);
        return allProducts.add((ProductCellarable) newProduct);
    }


    @Override
    public String toString() {
        return "Cellar{" +
                "light=" + light +
                ", allProducts=" + allProducts +
                '}';
    }
}
