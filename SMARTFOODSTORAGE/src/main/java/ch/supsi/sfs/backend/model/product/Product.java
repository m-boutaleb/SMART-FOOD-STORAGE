package ch.supsi.sfs.backend.model.product;

import java.util.Objects;

public abstract class Product {
    private final String barCode;
    private final String description;
    private final double weight;
    private int quantity;
    private int consummation;

    protected Product(final String barCode, final String description, final double weight,final int quantity) {
        this.barCode = barCode;
        this.description = description;
        this.weight = weight;
        this.quantity=quantity;
        consummation=0;
    }

    public abstract String getProductType();

    public int getConsummation() {
        return consummation;
    }

    public void incrementConsummation(){
        this.consummation++;
    }

    public double getWeight() {
        return weight;
    }

    public String getBarCode() {
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
                ", type="+ getProductType()+
                ", consummation: "+ getConsummation()+
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
        return Objects.hash(barCode);
    }
}
