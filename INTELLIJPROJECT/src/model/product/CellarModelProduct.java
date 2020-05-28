package model.product;


public abstract class CellarModelProduct extends Product{
    public CellarModelProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }
}
