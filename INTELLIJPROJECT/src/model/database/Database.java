package model.database;

import ch.industry4_0.dm.FieldDesc;
import ch.industry4_0.dm.Measurement;
import ch.industry4_0.exception.DatabaseSessionException;
import ch.industry4_0.influx.connector.InfluxConnector;
import model.product.*;
import org.influxdb.dto.Query;

/**
 * Quando si vuole determinare la tipologia del prodotto si guarda l'ultimo numero del barcode modulto 6:
 * 0: AboveZeroProduct
 * 1: BelowZeroProduct
 * 2: DisposableProduct
 * 3: LiquidProduct
 * 4: MultiUseProduct
 * 5: SolidProduct
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
    
    private void initMeasurementFields(){
        freezer.addField("barcode", FieldDesc.Type.NUMBER);
        freezer.addField("quantity", FieldDesc.Type.NUMBER);
        freezer.addField("description", FieldDesc.Type.STRING);
        freezer.addField("weight", FieldDesc.Type.NUMBER);
        freezer.addField("temperature", FieldDesc.Type.NUMBER);
        freezer.addField("product_type", FieldDesc.Type.STRING);

        cellar.addField("barcode", FieldDesc.Type.NUMBER);
        cellar.addField("quantity", FieldDesc.Type.NUMBER);
        cellar.addField("description", FieldDesc.Type.STRING);
        cellar.addField("weight", FieldDesc.Type.NUMBER);
        cellar.addField("brightness", FieldDesc.Type.NUMBER);
        cellar.addField("product_type", FieldDesc.Type.STRING);

        fridge.addField("barcode", FieldDesc.Type.NUMBER);
        fridge.addField("quantity", FieldDesc.Type.NUMBER);
        fridge.addField("description", FieldDesc.Type.STRING);
        fridge.addField("weight", FieldDesc.Type.NUMBER);
        fridge.addField("temperature", FieldDesc.Type.NUMBER);
        fridge.addField("product_type", FieldDesc.Type.STRING);

        pantry.addField("barcode", FieldDesc.Type.NUMBER);
        pantry.addField("quantity", FieldDesc.Type.NUMBER);
        pantry.addField("description", FieldDesc.Type.STRING);
        pantry.addField("weight", FieldDesc.Type.NUMBER);
        pantry.addField("product_type", FieldDesc.Type.STRING);
    }
    
    public static Database getInstance(){
        return (instance==null)?(instance=new Database()):instance;
    };

    public <T extends Product> void saveCellarProduct(final T product, final double light){
        cellar.save(product.getBarCode(),product.getQuantity(),  product.getDescription(), product.getWeight(),light);
    }

    public <T extends Product> void saveFreezeProduct(final T product, final double temperature){
        freezer.save(product.getBarCode(),product.getQuantity(),  product.getDescription(), product.getWeight(),temperature);
    }

    public <T extends Product> void saveFridgeProduct(final T product, final double temperature){
        fridge.save(product.getBarCode(),product.getQuantity(),  product.getDescription(), product.getWeight(),temperature);
    }

    public <T extends Product> void savePantryProduct(final T product){
        fridge.save(product.getBarCode(),product.getQuantity(),  product.getDescription(), product.getWeight());
    }
}
