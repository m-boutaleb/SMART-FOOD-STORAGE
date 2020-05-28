package main;

public class AboveZeroProduct extends Product {
    public AboveZeroProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();
    }
}
