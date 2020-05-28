package main;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Associazione prodotto sezione avviene tramite i primi numeri del product-barcode. Se il bar-code
 * ha come primo elemento un numero compreso tra 0-3 ed è diverso da uno incontrato precedentemente
 * allora vuoldire che appartiene alla categoria n dove n[0-3]:
 * 0: FreezeProduct
 * 1: FreshProduct
 * 2: CellarProduct
 * 3: OtherProduct
 * 
 * Quando si vuole determinare la tipologia del prodotto si guarda l'ultimo numero del barcode modulto 6:
 * 0: AboveZeroProduct
 * 1: BelowZeroProduct
 * 2: DisposableProduct
 * 3: LiquidProduct
 * 4: MultiUseProduct
 * 5: SolidProduct
 */
public abstract class Product {
    public Set<ProductCategory> getProductCategories() {
        return productCategories;
    }

    private final Set<ProductCategory> productCategories;
    private final long barCode;
    private final String description;
    private int quantity;
    private final double weight;
    
    public Product(final long barCode, final  String description, final int quantity, final double weight) {
        this.barCode = barCode;
        this.description = description;
        this.quantity = quantity;
        this.weight=weight;
        this.productCategories =getCategoriesByBarCode(barCode);
    }

    private Set<ProductCategory> getCategoriesByBarCode(final long barcode){
        String barcodeStr=String.valueOf(barcode);
        Set<ProductCategory> categories= new HashSet<>();
        int value;
        boolean stop=false;
        for(int i=0 ; i<4 && !stop; i++)
            stop =(value = Integer.parseInt((String.valueOf(barcodeStr.charAt(i)))))> 3 || !categories.add(value == 0 ?
                    ProductCategory.FREEZEPRODUCT : value == 1 ? ProductCategory.FRESHPRODUCT
                    : value == 2 ? ProductCategory.CELLARPRODUCT : ProductCategory.PANTRYPRODUCT);
        return categories;
    }

    public double getWeight() {
        return weight;
    }
    
    

    public long getBarCode() {
        return barCode;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productTypes=" + productCategories +
                ", barCode=" + barCode +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", weight=" + weight +
                '}';
    }
    
    public abstract String getType();
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;
        return barCode == product.barCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productCategories);
    }
}
