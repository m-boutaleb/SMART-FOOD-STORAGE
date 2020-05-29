package model.product;

import java.util.Objects;

public abstract class Product {
    private final long barCode;
    private final String description;
    private int quantity;
    private final double weight;

    protected Product(final long barCode, final String description, final double weight,final int quantity) {
        this.barCode = barCode;
        this.description = description;
        this.weight = weight;
        this.quantity=quantity;
    }

    public abstract String getProductType();

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
