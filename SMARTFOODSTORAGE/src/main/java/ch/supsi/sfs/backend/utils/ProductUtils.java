package ch.supsi.sfs.backend.utils;

import java.util.concurrent.ThreadLocalRandom;

public class ProductUtils {
    public static final int NR_BARCODENUMBERS=12;
    public static final int NR_TYPES=7;
    public static final int MAX_QTY=5;
    public static final double PRODUCT_MIN_FREEZE_TEMPERATURE =-20;
    public static final double PRODUCT_MAX_FREEZE_TEMPERATURE =0;
    public static final double PRODUCT_MIN_FRESH_TEMPERATURE =0;
    public static final double PRODUCT_MAX_FRESH_TEMPERATURE =15;
    private static final String[] aboveZeroProductsDesc =new String[]{"PIZZA SURGELATA", "SALMONE FRESCO", "POMODORINI FRESCHI"};
    private static final String[] belowZeroProductsDesc =new String[]{"PESCE MERLUZZO", "GELATO ALLA PANNA", "GELATO ALLA PANNA SAMMONTANA", "BIRRA APPENZELLER IPA",};
    private static final String[] disposableProductsDesc =new String[]{"GUANTI MONOUSO", "CARTA IGIENICA", "TOVAGLIOLI"};
    private static final String[] fermentedProductsDesc =new String[]{"GORGONZOLA REGGIANO", "VINO DEL NONNO", "SALSICCIA ESSICCATA"};
    private static final String[] liquidProductsDesc =new String[]{"LATTE", "COCACOLA LIGHT", "FANTA ZERO"};
    private static final String[] multiUseProductsDesc =new String[]{"SPUGNA DA CUCINA", "STRACCIO TAVOLA", "BICCHIERE RIEDEL"};
    private static final String[] solidProductsDesc =new String[]{"SCOPA PER PULIRE", "COLLA VINILICA", "TERRICCIO PIANTE", "BIRRA APPENZELLER 33CL WEIZEN"};

    public static String getRandomAZPDesc(){
        return aboveZeroProductsDesc[ThreadLocalRandom.current().nextInt(0, aboveZeroProductsDesc.length)];
    }
    public static String getRandomBZPDesc(){
        return belowZeroProductsDesc[ThreadLocalRandom.current().nextInt(0, belowZeroProductsDesc.length)];
    }
    public static String getRandomDPDesc(){
        return disposableProductsDesc[ThreadLocalRandom.current().nextInt(0, disposableProductsDesc.length)];
    }
    public static String getRandomFPDesc(){
        return fermentedProductsDesc[ThreadLocalRandom.current().nextInt(0, fermentedProductsDesc.length)];
    }
    public static String getRandomLPDesc(){
        return liquidProductsDesc[ThreadLocalRandom.current().nextInt(0, liquidProductsDesc.length)];
    }
    public static String getRandomMUPDesc(){
        return multiUseProductsDesc[ThreadLocalRandom.current().nextInt(0, multiUseProductsDesc.length)];
    }
    public static String getRandomSPDesc(){
        return solidProductsDesc[ThreadLocalRandom.current().nextInt(0, solidProductsDesc.length)];
    }
    public static int getRandomQuantity(){
        return ThreadLocalRandom.current().nextInt(1, MAX_QTY);
    }
    public static double getRandomFreezeTemp(){
        return ThreadLocalRandom.current().nextDouble(PRODUCT_MIN_FREEZE_TEMPERATURE, PRODUCT_MAX_FREEZE_TEMPERATURE);
    }
    public static double getRandomFreshTemp() {
        return ThreadLocalRandom.current().nextDouble(PRODUCT_MIN_FRESH_TEMPERATURE, PRODUCT_MAX_FRESH_TEMPERATURE);
    }
}
