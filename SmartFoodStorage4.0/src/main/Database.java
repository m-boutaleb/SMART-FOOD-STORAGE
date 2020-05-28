package main;

import ch.industry4_0.dm.FieldDesc;
import ch.industry4_0.dm.Measurement;
import ch.industry4_0.exception.DatabaseSessionException;
import ch.industry4_0.influx.connector.InfluxConnector;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static Database instance;
    private final ProductSection freezer;
    private final ProductSection fridge;
    private final ProductSection pantry;
    private final ProductSection cellar;

    private Database(){
        freezer=(ProductSection) new Freezer(0d);      
        cellar=new Cellar(0d);
        fridge=(ProductSection) new Fridge(0d);
        pantry=new Pantry();

        try {
            initDatabase();
        } catch (DatabaseSessionException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initDatabase() throws DatabaseSessionException{
        InfluxConnector ic = InfluxConnector.getInstance();
        ic.init("http://127.0.0.1:8086","smartfoodstoragedb","root", "root");
       
        Measurement freezer = ic.createMeasurement("freezer", "autogen");
        freezer.addField("barcode", FieldDesc.Type.NUMBER);
        freezer.addField("quantity", FieldDesc.Type.NUMBER);
        freezer.addField("description", FieldDesc.Type.STRING);
        freezer.addField("weight", FieldDesc.Type.NUMBER);
        freezer.addField("temperature", FieldDesc.Type.NUMBER);
        freezer.addField("product_type", FieldDesc.Type.STRING);


        Measurement cellar = ic.createMeasurement("cellar", "autogen");
        cellar.addField("barcode", FieldDesc.Type.NUMBER);
        cellar.addField("quantity", FieldDesc.Type.NUMBER);
        cellar.addField("description", FieldDesc.Type.STRING);
        cellar.addField("weight", FieldDesc.Type.NUMBER);
        cellar.addField("luminosity", FieldDesc.Type.NUMBER);
        cellar.addField("product_type", FieldDesc.Type.STRING);

        Measurement fridge = ic.createMeasurement("fridge", "autogen");
        fridge.addField("barcode", FieldDesc.Type.NUMBER);
        fridge.addField("quantity", FieldDesc.Type.NUMBER);
        fridge.addField("description", FieldDesc.Type.STRING);
        fridge.addField("weight", FieldDesc.Type.NUMBER);
        fridge.addField("temperature", FieldDesc.Type.NUMBER);
        fridge.addField("product_type", FieldDesc.Type.STRING);

        Measurement pantry = ic.createMeasurement("pantry", "autogen");
        pantry.addField("barcode", FieldDesc.Type.NUMBER);
        pantry.addField("quantity", FieldDesc.Type.NUMBER);
        pantry.addField("description", FieldDesc.Type.STRING);
        pantry.addField("weight", FieldDesc.Type.NUMBER);
        pantry.addField("product_type", FieldDesc.Type.STRING);
    }
    
    public static Database getInstance(){
        return (instance==null)?(instance=new Database()):instance;
    };
    
    public boolean saveData(final long barcodeNumber,final String description,final int quantity,final double weight){
        final long productType=barcodeNumber%10%6;
        Product newProduct=(Product)(productType==0?new AboveZeroProduct(barcodeNumber, description, quantity, weight)
                :productType==1?new  BelowZeroProduct(barcodeNumber, description, quantity, weight):productType==2?
                new DisposableProduct(barcodeNumber, description, quantity, weight):productType==3?
                new LiquidProduct(barcodeNumber, description, quantity, weight):productType==4?
                new MultiUseProduct(barcodeNumber, description, quantity, weight):
                new SolidProduct(barcodeNumber, description, quantity, weight));
        try{
        return newProduct.getProductCategories().contains(ProductCategory.FREEZEPRODUCT)?freezer.add(newProduct):
                newProduct.getProductCategories().contains(ProductCategory.FRESHPRODUCT)?fridge.add(newProduct):
                newProduct.getProductCategories().contains(ProductCategory.PANTRYPRODUCT)?pantry.add(newProduct):cellar.add(newProduct);
        
        }catch(Exception e){};
        return false;
    }
}
