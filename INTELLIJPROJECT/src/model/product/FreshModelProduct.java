package model.product;


public abstract class FreshModelProduct extends Product{
    private final double freshTemperature;

    public FreshModelProduct(final long barCode, final String description, final int quantity, final double weight, final double freshTemperature) {
        super(barCode, description, quantity, weight);
        this.freshTemperature = freshTemperature;
    }

}
