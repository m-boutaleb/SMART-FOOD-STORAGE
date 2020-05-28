package main;

import model.DisposableProduct;
import model.MultiUseProduct;
import model.product.Product;
import model.product.ProductCategory;
import model.section.Cellar;
import model.section.Freezer;
import model.section.Fridge;
import model.section.Pantry;
import model.section.service.ProductSection;
import model.LiquidProduct;
import model.SolidProduct;

import java.util.Arrays;

/**
 * Associazione prodotto sezione avviene tramite i primi numeri del product-barcode. Se il bar-code
 * ha come primo elemento un numero compreso tra 0-3 ed è diverso da uno incontrato precedentemente
 * allora vuoldire che appartiene alla categoria n dove n[0-3]:
 * 0: FreezeProduct
 * 1: FreshProduct
 * 2: CellarProduct
 * 3: OtherProduct
 */
public class Test {
    static boolean addProductToSection(final Product... products){
        Arrays.stream(products).forEach(product -> {
            var allProdTypes = product.getProductCategories();
            try {
                boolean value=allProdTypes.contains(ProductCategory.FREEZEPRODUCT) ?
                        freeze.add(product) : allProdTypes.contains(ProductCategory.FRESHPRODUCT) ?
                        fridge.add(product) : allProdTypes.contains(ProductCategory.CELLARPRODUCT) ?
                                cellar.add(product) : pantry.add(product);
            } catch (Exception e) {
                System.out.println("SOMETHNIG WENT WRONG WITH THE SECTION!");
            }
        });
        return true;
    }

    private final static ProductSection freeze= new Freezer(1.1d);
    private final static ProductSection fridge= new Fridge(10.1d);
    private final static ProductSection pantry=new Pantry();
    private final static ProductSection cellar=new Cellar(20.1d);

    public static void main(String[] args) {

        Product water=new LiquidProduct(3411235132L, "Acqua Frizzante", 12, 20d);
        Product meet=new SolidProduct(12411235132L, "Carne Bovina", 1, 3.2d);
        Product lighter= new MultiUseProduct(20012351251L, "Lighter to turn on gas", 2, 1.1d);
        Product toiletPaper= new DisposableProduct(1100123551L, "Paper for WC", 10, 13.1d);
        Product milk= new MultiUseProduct(012311120L, "Milk UHT", 6, 17d);

        try {
            addProductToSection(water, meet,lighter, toiletPaper, milk);
        } catch (Exception e) {
            System.out.println("SOMETHING WENT WRONG WITH ADDING PRODUCT TO SECTION");
        }

        System.out.println(freeze);
        System.out.println(pantry);
        System.out.println(cellar);
        System.out.println(fridge);
    }
}
