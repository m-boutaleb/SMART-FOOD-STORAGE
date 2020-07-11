package ch.supsi.sfs.backend.database;

import ch.industry4_0.dm.FieldDesc;
import ch.industry4_0.dm.Measurement;
import ch.industry4_0.exception.DatabaseSessionException;
import ch.industry4_0.influx.connector.InfluxConnector;
import ch.supsi.sfs.backend.model.product.*;

/**
 * Classe che si connette al server di INFLUXDB sulla porta 8086 con il db denominato
 * smartfoodstoragedb. Attenzione non crea alcun database. Tutti di database devono già essere stati
 * creati dalla shell messa a disposizione.
 * Una volta creato il db dalla shell si può impostare il nome nella variabile DB_NAME
 */
public class Database {
    private final static String LOCAL_HOST_PORT="http://127.0.0.1:8086";
    private final static String DB_NAME="smartfoodstoragedb";
    private final static String DB_USER="root";
    private final static String DB_PASSWORD="root";

    private static Database instance;
    private final Measurement freezer;
    private final Measurement cellar;
    private final Measurement fridge;
    private final Measurement pantry;
    private final InfluxConnector ic;

    /**
     * Ottiene un instanza di influxConnector e crea tutti i measurements
     * chiamando il metodo che si occupa di creare tutti i field dei measurements
     */
    private Database(){
        ic = InfluxConnector.getInstance();
        try {
            ic.init(LOCAL_HOST_PORT,DB_NAME,DB_USER, DB_PASSWORD);
        } catch (DatabaseSessionException e) {
            e.printStackTrace();
        }
        freezer = ic.createMeasurement("freezer", "autogen");
        cellar = ic.createMeasurement("cellar", "autogen");
        fridge = ic.createMeasurement("fridge", "autogen");
        pantry = ic.createMeasurement("pantry", "autogen");
        initMeasurementFields();
    }

    public void closeConnection(){
        ic.close();
    }

    /**
     * Creat tutte i measurement(che corrispondono alle categorie) con tutti i campi
     */
    private void initMeasurementFields(){
        freezer.addField("barcode", FieldDesc.Type.STRING);
        freezer.addField("quantity", FieldDesc.Type.NUMBER);
        freezer.addField("description", FieldDesc.Type.STRING);
        freezer.addField("weight", FieldDesc.Type.NUMBER);
        freezer.addField("temperature", FieldDesc.Type.NUMBER);
        freezer.addField("product_type", FieldDesc.Type.STRING);
        freezer.addField("consummation", FieldDesc.Type.NUMBER);
        freezer.addTag("barcode_tag");

        cellar.addField("barcode", FieldDesc.Type.STRING);
        cellar.addField("quantity", FieldDesc.Type.NUMBER);
        cellar.addField("description", FieldDesc.Type.STRING);
        cellar.addField("weight", FieldDesc.Type.NUMBER);
        cellar.addField("brightness", FieldDesc.Type.NUMBER);
        cellar.addField("product_type", FieldDesc.Type.STRING);
        cellar.addField("consummation", FieldDesc.Type.NUMBER);
        cellar.addTag("barcode_tag");

        fridge.addField("barcode", FieldDesc.Type.STRING);
        fridge.addField("quantity", FieldDesc.Type.NUMBER);
        fridge.addField("description", FieldDesc.Type.STRING);
        fridge.addField("weight", FieldDesc.Type.NUMBER);
        fridge.addField("temperature", FieldDesc.Type.NUMBER);
        fridge.addField("product_type", FieldDesc.Type.STRING);
        fridge.addField("consummation", FieldDesc.Type.NUMBER);
        fridge.addTag("barcode_tag");

        pantry.addField("barcode", FieldDesc.Type.STRING);
        pantry.addField("quantity", FieldDesc.Type.NUMBER);
        pantry.addField("description", FieldDesc.Type.STRING);
        pantry.addField("weight", FieldDesc.Type.NUMBER);
        pantry.addField("product_type", FieldDesc.Type.STRING);
        pantry.addField("consummation", FieldDesc.Type.NUMBER);
        pantry.addTag("barcode_tag");
    }
    
    public static Database getInstance(){
        return (instance==null)?(instance=new Database()):instance;
    };

    /**
     * Salva un prodotto nel measurement del db impostando anche la temperatura
     * @param product prodotto da salvare
     * @param light luce rilevata correntemente
     * @param <T> un qualunque oggetto che sia un sottotipo della classe Prodotto
     */
    public <T extends Product> void saveCellarProduct(final T product, final double light){
        System.out.println(product+" Cellar light: "+light);
        cellar.save(product.getBarCode(),product.getQuantity(),  product.getDescription(), product.getWeight(),
                light,product.getProductType(), product.getConsummation(), product.getBarCode());
    }

    /**
     * Salva un prodotto nel measurement del db impostando anche la temperatura
     * @param product prodotto da salvare
     * @param <T> un qualunque oggetto che sia un sottotipo della classe Prodotto
     */
    public <T extends Product> void saveFreezeProduct(final T product, final double temperature){
        System.out.println(product+" Freeze temperature: "+temperature);
        freezer.save(product.getBarCode(),product.getQuantity(),product.getDescription(), product.getWeight(),temperature, product.getProductType(),
                product.getConsummation(), product.getBarCode());
    }

    /**
     * Salva un prodotto nel measurement del db impostando anche la temperatura
     * @param product prodotto da salvare
     * @param <T> un qualunque oggetto che sia un sottotipo della classe Prodotto
     */
    public <T extends Product> void saveFridgeProduct(final T product, final double temperature){
        System.out.println(product+" Fridge temperature: "+temperature);
        fridge.save(product.getBarCode(),product.getQuantity(),product.getDescription()
                , product.getWeight(),temperature,product.getProductType(),product.getConsummation(), product.getBarCode());
    }

    /**
     * Salva prodotto nel measurement del db
     * @param product prodotto da salvare
     * @param <T> un qualunque oggetto che sia un sottotipo della classe Prodotto
     */
    public <T extends Product> void savePantryProduct(final T product){
        System.out.println(product);
        pantry.save(product.getBarCode(),product.getQuantity(),product.getDescription(), product.getWeight(),
                product.getProductType(),product.getConsummation(), product.getBarCode());
    }
}
