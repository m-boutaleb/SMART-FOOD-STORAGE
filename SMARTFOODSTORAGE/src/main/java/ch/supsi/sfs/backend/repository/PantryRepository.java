package ch.supsi.sfs.backend.repository;

import java.util.HashSet;
import java.util.Set;

import ch.supsi.sfs.backend.database.Database;
import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductPantriable;
import ch.supsi.sfs.backend.model.product.type.LiquidProduct;
import ch.supsi.sfs.backend.repository.property.Countable;
import ch.supsi.sfs.backend.repository.property.CrudRepository;

import static ch.supsi.sfs.backend.utils.RepositoryUtils.PANTRY_MAX_QTY;

public class PantryRepository implements CrudRepository<ProductPantriable>, Countable {
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
    public boolean add(final ProductPantriable product){
        if(checkStatus()[0]==2)
            return false;
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
    public int[] checkStatus() {
        int totalQty=getTotalQty();
        return new int[]{(totalQty)>PANTRY_MAX_QTY?2:totalQty==0?0:1, 1};
    }

    @Override
    public int getTotalQty() {
        return allProducts.stream().parallel().map(p->(Product)p).mapToInt(Product::getQuantity).sum();
    }

    @Override
    public boolean remove(final String barcode) {
        final Product sameProduct=new LiquidProduct(barcode, "", 234, 1234, 213, 324);
        final boolean found=allProducts.contains(sameProduct);
        System.out.println((found?"Find element in pantry measurements":"Element not found in pantry measurements"));
        if(found){
            final Product productFound=allProducts.stream().parallel().
                    map(p->(Product)p).filter(sameProduct::equals).findFirst().orElse(null);
            int currentQty=productFound.getQuantity();
            if(currentQty==0)
                return false;
            int qtyToRemove=(int)(Math.random() * currentQty + 1);
            productFound.setQuantity(currentQty-qtyToRemove);
            productFound.incrementConsummation();
            database.savePantryProduct(productFound);
        }
        return found;
    }

    @Override
    public String toString() {
        return "Pantry{" +
                "allProducts=" + allProducts +
                '}';
    }
}
