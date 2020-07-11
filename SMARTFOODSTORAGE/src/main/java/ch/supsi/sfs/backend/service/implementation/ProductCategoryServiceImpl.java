package ch.supsi.sfs.backend.service.implementation;

import ch.supsi.sfs.backend.model.product.Product;
import ch.supsi.sfs.backend.model.product.type.*;
import ch.supsi.sfs.backend.repository.CellarRepository;
import ch.supsi.sfs.backend.repository.FreezerRepository;
import ch.supsi.sfs.backend.repository.FridgeRepository;
import ch.supsi.sfs.backend.repository.PantryRepository;
import ch.supsi.sfs.backend.service.ProductCategoryService;

import java.util.concurrent.ThreadLocalRandom;

import static ch.supsi.sfs.backend.utils.ProductUtils.*;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    private static ProductCategoryServiceImpl instance;
    private final PantryRepository pantryRepository;
    private final CellarRepository cellarRepository;
    private final FreezerRepository freezerRepository;
    private final FridgeRepository fridgeRepository;

    private ProductCategoryServiceImpl() {
        pantryRepository=PantryRepository.getInstance();
        cellarRepository=CellarRepository.getInstance();
        freezerRepository=FreezerRepository.getInstance();
        fridgeRepository=FridgeRepository.getInstance();
    }

    /**
     * @return 1 se il limite è ok 0 se i prodotti stanno per finire altrimenti 2 se ha superato il
     * massimo che si può avere in quella sezione
     */
    @Override
    public int[] checkCellarStatus(){
        return cellarRepository.checkStatus();
    }

    /**
     * @return 1 se il limite è ok 0 se i prodotti stanno per finire altrimenti 2 se ha superato il
     * massimo che si può avere in quella sezione
     */
    @Override
    public int[] checkFridgeStatus(){
        return fridgeRepository.checkStatus();
    }

    /**
     * @return 1 se il limite è ok 0 se i prodotti stanno per finire altrimenti 2 se ha superato il
     * massimo che si può avere in quella sezione
     */
    @Override
    public int[] checkFreezerStatus(){
        return freezerRepository.checkStatus();
    }

    @Override
    public void setCellarStatus(final double cellarLight) {
        cellarRepository.setLight(cellarLight);
    }

    @Override
    public void setFridgeStatus(final double temperature) {
        fridgeRepository.setTemperature(temperature);
    }

    @Override
    public void setFreezerStatus(final double temperature) {
        freezerRepository.setTemperature(temperature);
    }

    @Override
    public void removeProduct(final String barcode) {
        //At least remove it one time
        boolean removed=fridgeRepository.remove(barcode)||
        pantryRepository.remove(barcode)||
        cellarRepository.remove(barcode)||
        freezerRepository.remove(barcode);
    }

    /**
     * @return 1 se il limite è ok 0 se i prodotti stanno per finire altrimenti 2 se ha superato il
     * massimo che si può avere in quella sezione
     */
    @Override
    public int[] checkPantryStatus(){
        return pantryRepository.checkStatus();
    }


    public static ProductCategoryService getInstance() {
        return (instance==null)?(instance=new ProductCategoryServiceImpl()):instance;
    }

    @Override
    public Product productByBarcodeAndSave(final String barcode, final double weight) {
        final int type=Integer.parseInt(String.valueOf(barcode.charAt(NR_BARCODENUMBERS-1)))%NR_TYPES;
        return generateRandomProduct(type, barcode, weight);
    }

    private Product generateRandomProduct(final int type, final String barcode, final double weight) {
        Product product=null;
        final int randNr=ThreadLocalRandom.current().nextInt(0, 2);
        boolean added;
        switch(type) {
            case 0:
                freezerRepository.add(new BelowZeroProduct(barcode, getRandomAZPDesc(), weight, getRandomQuantity(), getRandomFreezeTemp()));
                break;
            case 1:
                final AboveZeroProduct newBZProd=new AboveZeroProduct(barcode, getRandomBZPDesc(), weight, getRandomQuantity(), getRandomFreshTemp());
                added=randNr==1?pantryRepository.add(newBZProd):fridgeRepository.add(newBZProd);
                break;
            case 2:
                pantryRepository.add(new DisposableProduct(barcode, getRandomDPDesc(), weight, getRandomQuantity()));
                break;
            case 3:
                cellarRepository.add(new FermentedProduct(barcode, getRandomFPDesc(), weight, getRandomQuantity()));
                break;
            case 4:
                final LiquidProduct newLPProd=new LiquidProduct(barcode, getRandomLPDesc(), weight,getRandomFreezeTemp(), getRandomQuantity(), getRandomFreshTemp());
                added=randNr==1?freezerRepository.add(newLPProd):fridgeRepository.add(newLPProd);
                break;
            case 5:
                final MultiUseProduct newMUProd=new MultiUseProduct(barcode, getRandomMUPDesc(), weight, getRandomQuantity());
                added=randNr==1?cellarRepository.add(newMUProd):pantryRepository.add(newMUProd);
                break;
            default:
                final SolidProduct newSProd=new SolidProduct(barcode, getRandomSPDesc(), weight, getRandomQuantity(), getRandomFreshTemp());
                added=randNr==1?pantryRepository.add(newSProd):fridgeRepository.add(newSProd);
                break;
        }
        return product;
    }
}
