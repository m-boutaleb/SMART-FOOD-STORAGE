package ch.supsi.sfs.backend.repository;

import ch.supsi.sfs.backend.database.Database;
import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductFreezable;
import ch.supsi.sfs.backend.model.product.type.LiquidProduct;
import java.util.ArrayList;

import static ch.supsi.sfs.backend.utils.RepositoryUtils.FREEZER_MAX_WEIGHT;
import static ch.supsi.sfs.backend.utils.RepositoryUtils.MAX_FREEZER_TEMPERATURE;

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
            database.saveFreezeProduct(found, temperature);
            return true;
        }
        database.saveFreezeProduct(newProduct, temperature);
        return allProducts.add((ProductFreezable) newProduct);
    }

    @Override
    public int[] checkStatus() {
        double totalWeight=getTotalWeight();
        return new int[]{totalWeight>FREEZER_MAX_WEIGHT?2:totalWeight==0?0:1, temperature>MAX_FREEZER_TEMPERATURE?0:1};
    }

    private double getTotalWeight() {
        return allProducts.stream().map(p->(Product)p).mapToDouble(Product::getWeight).sum();
    }

    @Override
    public boolean remove(final String barcode) {
        final boolean found=allProducts.contains(new LiquidProduct(barcode, "", 234, 1234, 213, 324));
        System.out.println((found?"Find element in freezer measurements":"Element not found in freezer measurements"));
        if(found){
            final Product productFound=allProducts.stream().parallel().
                    map(p->(Product)p).filter(p->barcode.equals(p.getBarCode())).findFirst().orElse(null);
            int currentQty=productFound.getQuantity();
            if(currentQty==0)
                return false;
            productFound.setQuantity(currentQty-((int)(Math.random() * currentQty + 1)));
            database.saveFreezeProduct(productFound, temperature);
        }
        return found;
    }

    @Override
    public String toString() {
        return "Freezer{" +
                "temperature=" + temperature +
                ", allProducts=" + allProducts +
                '}';
    }
}
