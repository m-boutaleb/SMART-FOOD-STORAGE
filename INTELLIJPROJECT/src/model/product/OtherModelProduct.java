package model.product;


public abstract class OtherModelProduct extends Product{
    private final String additionalInformation;

    public OtherModelProduct(final long barCode, final String description, final int quantity, final double weight, final String additionalInformation) {
        super(barCode, description, quantity, weight);
        this.additionalInformation = additionalInformation;
    }

}
