package repository;

import java.util.HashSet;
import java.util.Set;

import database.Database;
import model.product.Product;
import model.product.ProductPantriable;
import repository.crudservice.CrudRepository;

public class PantryRepository implements CrudRepository<ProductPantriable> {
    private static PantryRepository instance;
    private final Set<ProductPantriable> allProducts;
    private final Database database;

    private PantryRepository(){
        this.database = Database.getInstance();
        this.allProducts =new HashSet<>();
    }

    public static PantryRepository getInstance() {
        return (instance==null)?(instance=new PantryRepository()):instance;
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
            found.setQuantity(found.getQuantity() + newProduct.getQuantity());
            database.savePantryProduct(found);
            return true;
        }
        database.savePantryProduct(newProduct);
        return allProducts.add((ProductPantriable) newProduct);
    }

    @Override
    public String toString() {
        return "Pantry{" +
                "allProducts=" + allProducts +
                '}';
    }
}
