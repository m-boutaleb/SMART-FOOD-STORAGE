package utils;

import java.util.concurrent.ThreadLocalRandom;

public class ProductUtils {
    public static final int NR_BARCODENUMBERS=12;
    public static final int NR_TYPES=7;
    public static final int MAX_QTY=2;
    public static final double MIN_FREEZE_TEMPERATURE=-20;
    public static final double MAX_FREEZE_TEMPERATURE=0;
    public static final double MIN_FRESH_TEMPERATURE=0;
    public static final double MAX_FRESH_TEMPERATURE=15;
    private static final String[] aboveZeroProductsDesc =new String[]{"PIZZA SURGELATA", "SALMONE FRESCO", "POMODORINI FRESCHI"};
    private static final String[] belowZeroProductsDesc =new String[]{"PESCE MERLUZZO", "GELATO ALLA PANNA", "GELATO ALLA PANNA"};
    private static final String[] disposableProductsDesc =new String[]{"GUANTI MONOUSO", "CARTA IGIENICA", "TOVAGLIOLI"};
    private static final String[] fermentedProductsDesc =new String[]{"GORGONZOLA REGGIANO", "VINO DEL NONNO", "SALSICCIA ESSICCATA"};
    private static final String[] liquidProductsDesc =new String[]{"SGRASSATORE CUCINA", "COCACOLA LIGHT", "FANTA ZERO"};
    private static final String[] multiUseProductsDesc =new String[]{"SPUGNA DA CUCINA", "STRACCIO TAVOLA", "BICCHIERE RIEDEL"};
    private static final String[] solidProductsDesc =new String[]{"SCOPA PER PULIRE", "COLLA VINILICA", "TERRICCIO PIANTE"};

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
        return ThreadLocalRandom.current().nextInt(0, MAX_QTY);
    }
    public static double getRandomFreezeTemp(){
        return ThreadLocalRandom.current().nextDouble(MIN_FREEZE_TEMPERATURE, MAX_FREEZE_TEMPERATURE);
    }
    public static double getRandomFreshTemp() {
        return ThreadLocalRandom.current().nextDouble(MIN_FRESH_TEMPERATURE, MAX_FRESH_TEMPERATURE);
    }
}
