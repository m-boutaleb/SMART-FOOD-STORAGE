package ch.supsi.sfs.backend.repository;

import ch.supsi.sfs.backend.database.Database;
import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductCellarable;
import ch.supsi.sfs.backend.model.product.type.FermentedProduct;
import ch.supsi.sfs.backend.repository.property.Countable;
import ch.supsi.sfs.backend.repository.property.CrudRepository;

import java.util.HashSet;
import java.util.Set;

import static ch.supsi.sfs.backend.utils.RepositoryUtils.CELLAR_MAX_QTY;
import static ch.supsi.sfs.backend.utils.RepositoryUtils.MAX_BRIGHTNESS_VALUE;

public class CellarRepository implements CrudRepository<ProductCellarable>, Countable {
    private static CellarRepository instance;
    private double light;
    private final Set<ProductCellarable> allProducts;
    private final Database database;

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
    public boolean add(final ProductCellarable product) {
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
            database.saveCellarProduct(found, light);
            return true;
        }
        database.saveCellarProduct(newProduct, light);
        return allProducts.add((ProductCellarable) newProduct);
    }

    @Override
    public int[] checkStatus() {
        int totalQty=getTotalQty();
        return new int[]{(totalQty)>CELLAR_MAX_QTY?2:totalQty==0?0:1, light>MAX_BRIGHTNESS_VALUE?0:1};
    }

    @Override
    public int getTotalQty() {
        return allProducts.stream().parallel().map(p->(Product)p).mapToInt(Product::getQuantity).sum();
    }

    @Override
    public boolean remove(final String barcode) {
        final Product sameProduct=new FermentedProduct(barcode, "", 234, 1234);
        final boolean found=allProducts.contains(sameProduct);
        System.out.println((found?"Find element in cellar measurements":"Element not found in cellar measurements"));
        if(found){
            final Product productFound=allProducts.stream().parallel().
                    map(p->(Product)p).filter(sameProduct::equals).findFirst().orElse(null);
            int currentQty=productFound.getQuantity();
            if(currentQty==0)
                return false;
            int qtyToRemove=(int)(Math.random() * currentQty + 1);
            productFound.setQuantity(currentQty-qtyToRemove);
            productFound.incrementConsummation();
            database.saveCellarProduct(productFound, light);
        }
        return found;
    }

    @Override
    public String toString() {
        return "Cellar{" +
                "light=" + light +
                ", allProducts=" + allProducts +
                '}';
    }
}
