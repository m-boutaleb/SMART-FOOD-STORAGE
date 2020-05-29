package service.implementation;

import model.product.Product;
import model.product.type.*;
import repository.CellarRepository;
import repository.FreezerRepository;
import repository.FridgeRepository;
import repository.PantryRepository;
import service.ProductService;

import java.util.concurrent.ThreadLocalRandom;

import static utils.ProductUtils.*;

/**
 * Associazione prodotto sezione avviene tramite i primi numeri del product-barcode. Se il bar-code
 * ha come primo elemento un numero compreso tra 0-7 ed e' diverso da uno incontrato precedentemente
 * allora vuoldire che appartiene alla categoria n dove n equivale a:
 * 0: AboveZeroProduct
 * 1: BelowZeroProduct
 * 2: DisposableProduct
 * 3: FermentedProduct
 * 4: LiquidProduct
 * 5: MultiUseProduct
 * 6: SolidProduct
 */
public class ProductServiceImpl implements ProductService {
    private static ProductServiceImpl instance;
    private final PantryRepository pantryRepository;
    private final CellarRepository cellarRepository;
    private final FreezerRepository freezerRepository;
    private final FridgeRepository fridgeRepository;

    private ProductServiceImpl() {
        pantryRepository=PantryRepository.getInstance();
        cellarRepository=CellarRepository.getInstance();
        freezerRepository=FreezerRepository.getInstance();
        fridgeRepository=FridgeRepository.getInstance();
    }

    public static ProductService getInstance() {
        return (instance==null)?(instance=new ProductServiceImpl()):instance;
    }

    @Override
    public Product productByBarcodeAndSave(final long barcode, final double weight) {
        final String stringValue=String.valueOf(barcode);
        final int type=Integer.parseInt(String.valueOf(stringValue.charAt(NR_BARCODENUMBERS-1)));
        return generateRandomProduct(type, barcode, weight);
    }

    private Product generateRandomProduct(final int type, final long barcode, final double weight) {
        Product product=null;
        final int randNr=ThreadLocalRandom.current().nextInt(0, 2);
        boolean added;
        switch(type) {
            case 0:
                freezerRepository.add(new AboveZeroProduct(barcode, getRandomAZPDesc(), weight, getRandomQuantity(), getRandomFreezeTemp()));
                break;
            case 1:
                final BelowZeroProduct newBZProd=new BelowZeroProduct(barcode, getRandomBZPDesc(), weight, getRandomQuantity(), getRandomFreshTemp());
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
