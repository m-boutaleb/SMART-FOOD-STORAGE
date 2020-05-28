package main;

public class LiquidProduct extends Product {

    public LiquidProduct(final long barCode, final String description, final int quantity, final double weight) {
        super(barCode, description, quantity, weight);
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName();        
    }
}
