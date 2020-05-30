package ch.supsi.sfs.frontend.controller;

import org.iot.raspberry.grovepi.sensors.i2c.GroveRgbLcd;

import java.io.IOException;

import static ch.supsi.sfs.demo.DemoTest.GROVE_PI;

public class LedLcdViewer {
    private GroveRgbLcd lcdFreezer;
    private GroveRgbLcd lcdFridge;
    private GroveRgbLcd lcdCellar;
    private GroveRgbLcd lcdPantry;

    private static LedLcdViewer instance;

    private LedLcdViewer(){
        try {
            lcdFreezer=GROVE_PI.getLCD();
            lcdFridge=GROVE_PI.getLCD();
            lcdPantry=GROVE_PI.getLCD();
            lcdCellar=GROVE_PI.getLCD();
            lcdFreezer.setRGB(0,255,0);
            lcdFridge.setRGB(0,255,0);
            lcdCellar.setRGB(0,255,0);
            lcdPantry.setRGB(0, 255, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LedLcdViewer getInstance() {
        return instance==null?(instance=new LedLcdViewer()):instance;
    }

    public void showPantryResult(final int... result) throws IOException {
        boolean ok=result[0]==1&&result[1]==1;
        lcdPantry.setRGB(ok?0:255,ok?255:0, 0 );
        if(ok) {
            lcdPantry.setText("PANTRY STATUTS: OK");
            return;
        }
        final String[] OUTPUT_STATUS=new String[]{"EMPTY", "", "FULL"};
        lcdPantry.setText("FRIDGE STATUS:"+OUTPUT_STATUS[result[0]]);
    }

    public void showFridgeResult(final int... result) throws IOException {
        boolean ok=result[0]==1&&result[1]==1;
        lcdFridge.setRGB(ok?0:255,ok?255:0, 0 );
        if(ok) {
            lcdFridge.setText("FRIDGE STATUTS: OK");
            return;
        }
        final String[] OUTPUT_STATUS=new String[]{"EMPTY", "", "FULL"};
        final String[] OUTPUT_CONDITION=new String[]{"OVER-TEMPERATURE", "", ""};
        lcdFridge.setText("FRIDGE STATUS:"+OUTPUT_STATUS[result[0]]+" "+OUTPUT_CONDITION[result[1]]);
    }
    public void showFreezerResult(final int... result) throws IOException {
        boolean ok=result[0]==1&&result[1]==1;
        lcdFreezer.setRGB(ok?0:255,ok?255:0, 0 );
        if(ok) {
            lcdFreezer.setText("FREEZER STATUTS: OK");
            return;
        }
        final String[] OUTPUT_STATUS=new String[]{"EMPTY", "", "FULL"};
        final String[] OUTPUT_CONDITION=new String[]{"OVER-TEMPERATURE", "", ""};
        lcdFreezer.setText("FREEZER STATUS:"+OUTPUT_STATUS[result[0]]+" "+OUTPUT_CONDITION[result[1]]);
    }

    public void showCellarResult(final int... result) throws IOException {
        boolean ok=result[0]==1&&result[1]==1;
        lcdCellar.setRGB(ok?0:255,ok?255:0, 0 );
        if(ok) {
            lcdCellar.setText("CELLAR STATUTS: OK");
            return;
        }
        final String[] OUTPUT_STATUS=new String[]{"EMPTY", "", "FULL"};
        final String[] OUTPUT_CONDITION=new String[]{"OVER-LIGHT", "", ""};
        lcdCellar.setText("CELLAR STATUS:"+OUTPUT_STATUS[result[0]]+" "+OUTPUT_CONDITION[result[1]]);
    }
}
