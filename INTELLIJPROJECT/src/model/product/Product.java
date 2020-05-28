package model.product;

import java.util.Objects;

/**
 * Associazione prodotto sezione avviene tramite i primi numeri del product-barcode. Se il bar-code
 * ha come primo elemento un numero compreso tra 0-3 ed e' diverso da uno incontrato precedentemente
 * allora vuoldire che appartiene alla categoria n dove n[0-3]:
 * 0: FreezeProduct
 * 1: FreshProduct
 * 2: CellarProduct
 * 3: OtherProduct
 */
public abstract class Product {
    private final long barCode;
    private final String description;
    private int quantity;
    private final double weight;
    private final String productType;

    protected Product(final long barCode, final String description, final double weight, final String productType) {
        this.barCode = barCode;
        this.description = description;
        this.weight = weight;
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
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
                "barCode=" + barCode +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;
        return barCode == product.barCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barCode, description, quantity, weight);
    }
}
