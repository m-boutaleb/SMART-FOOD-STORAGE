package ch.supsi.sfs.backend.repository;

import ch.supsi.sfs.backend.database.Database;
import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.ProductFreshable;
import ch.supsi.sfs.backend.model.product.type.LiquidProduct;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static ch.supsi.sfs.backend.utils.RepositoryUtils.FRIDGE_MAX_WEIGHT;
import static ch.supsi.sfs.backend.utils.RepositoryUtils.MAX_FRIDGE_TEMPERATURE;

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
        if (checkStatus()[0]==2)
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
            database.saveFridgeProduct(found, temperature);
            return true;
        }
        database.saveFridgeProduct(newProduct, temperature);
        return allProducts.add((ProductFreshable) newProduct);
    }

    @Override
    public int[] checkStatus() {
        double totalWeight=getTotalWeight();
        return new int[]{totalWeight>FRIDGE_MAX_WEIGHT?2:totalWeight==0?0:1, temperature>MAX_FRIDGE_TEMPERATURE?0:1};
    }

    private double getTotalWeight() {
        return allProducts.stream().map(p->(Product)p).mapToDouble(Product::getWeight).sum();
    }

    @Override
    public boolean remove(final String barcode) {
        final boolean found=allProducts.contains(new LiquidProduct(barcode, "", 234, 1234, 213, 324));
        System.out.println((found?"Find element in fridge measurements":"Element not found in fridge measurements"));
        if(found){
            final Product productFound=allProducts.stream().parallel().
                    map(p->(Product)p).filter(p->barcode.equals(p.getBarCode())).findFirst().orElse(null);
            int currentQty=productFound.getQuantity();
            if(currentQty==0)
                return false;
            productFound.setQuantity(currentQty-((int)(Math.random() * currentQty + 1)));
            database.saveFridgeProduct(productFound, temperature);
        }
        return found;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "temperature=" + temperature +
                ", allProducts=" + allProducts +
                '}';
    }
}
