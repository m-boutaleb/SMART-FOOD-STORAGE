package model.product;


public abstract class FreezeModelProduct extends Product {
    private final double freezeTemperature;

    public FreezeModelProduct(final long barCode, final String description, final int quantity, final double weight, final double freezeTemperature) {
        super(barCode, description, quantity, weight);
        this.freezeTemperature = freezeTemperature;
    }
}
