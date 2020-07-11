package ch.supsi.sfs.backend.model.product;

import java.util.Objects;

/**
 * Classe Base che accomuna tutti i prodotti e le loro caratteristiche
 */
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

    /**
     * Questo metodo verrà poi implementato da ogni classe concreta che verrà poi istanziata
     * @return String rappresentante il nome della classe
     */
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

    /**
     * Usato in fase di testing della demo per vedere se i valori random letti
     * dai sensori venivano stampati corretamente
     * @return String rappresentante tutte le caratterstiche del prodotto
     */
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

    /**
     * Ogni oggetto è identificato dall'altro solo dal barcode
     * @param o
     * @return vero o false a seconda che i due oggetti abbiano lo stesso barcode o meno
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Product product = (Product) o;
        return barCode == product.barCode;
    }

    /**
     * Usato da Set all'interno delle diverse Repository
     * @return intero rappresentante l'indice dove verrà salvato il prodotto
     */
    @Override
    public int hashCode() {
        return Objects.hash(barCode);
    }
}
