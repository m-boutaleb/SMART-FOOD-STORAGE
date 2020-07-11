package ch.supsi.sfs.frontend.controller;

import ch.supsi.sfs.backend.controller.StorageController;
import ch.suspi.simulator.sensors.analog.light.LightSensorSimulator;
import ch.suspi.simulator.sensors.analog.loadcell.LoadCellSimulator;
import ch.suspi.simulator.sensors.barcode.random.RandomBarcodeSimulator;
import ch.suspi.simulator.sensors.digital.temphumidity.TemperatureAndHumiditySimulator;
import org.iot.raspberry.grovepi.sensors.synch.SensorMonitor;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static ch.supsi.sfs.backend.utils.RepositoryUtils.*;
import static ch.supsi.sfs.frontend.demo.DemoTest.GROVE_PI;

public class StationController {
    private static StationController instance;
    private final StorageController storageController;
    private RandomBarcodeSimulator barcodeGenerator;
    private SensorMonitor<Double> weigher;
    private LightSensorSimulator cellarLight;
    private final TemperatureAndHumiditySimulator temperatureFreezer;
    private final TemperatureAndHumiditySimulator temperatureFridge;
    private static final double RELATIVE_ERROR=10D;

    private final LedLcdViewer ledLcdViewer;

    private StationController(){
        ledLcdViewer = LedLcdViewer.getInstance();
        storageController=StorageController.getInstance();
        temperatureFridge=new TemperatureAndHumiditySimulator(GROVE_PI, 1, TemperatureAndHumiditySimulator.Type.DHT11);
        temperatureFreezer =new TemperatureAndHumiditySimulator(GROVE_PI, 2, TemperatureAndHumiditySimulator.Type.DHT21);
        try {
            cellarLight=new LightSensorSimulator(GROVE_PI, 3, "Cellar Light Random Generator");
            weigher=new SensorMonitor<Double>(new LoadCellSimulator(GROVE_PI, 6, "Weigher"), 200);
            barcodeGenerator=new RandomBarcodeSimulator("Barcode Generator");
        } catch (IOException e) {
            e.printStackTrace();
        }
        weigher.start();
    }

    public void genRepositoryRandomValues(){
        try {
            if(ThreadLocalRandom.current().nextInt(0, 10)%3==0) {
                final var fridgeTemp=ThreadLocalRandom.current().nextDouble(MIN_FRIDGE_TEMPERATURE,MAX_FRIDGE_TEMPERATURE+RELATIVE_ERROR);
                final var freezerTemp=ThreadLocalRandom.current().nextDouble(MIN_FREEZER_TEMPERATURE,MAX_FREEZER_TEMPERATURE+RELATIVE_ERROR);
                final var cellarValue=ThreadLocalRandom.current().nextDouble(MIN_BRIGHTNESS_VALUE,MAX_BRIGHTNESS_VALUE+RELATIVE_ERROR);
                temperatureFridge.setTemperature((int)fridgeTemp);
                temperatureFreezer.setTemperature((int)freezerTemp);
                cellarLight.setValue((int)cellarValue);
                storageController.setCellarLight(cellarValue);
                storageController.setFreezerTemp(freezerTemp);
                storageController.setFridgeTemp(fridgeTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void genBarcodeStatusAndSave(){
        try {
            storageController.saveProduct(barcodeGenerator.get().getText(), weigher.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static StationController getInstance() {
        return instance==null?(instance=new StationController()):instance;
    }

    public void checkAllRepos(){
        try {
            ledLcdViewer.showPantryResult(storageController.checkPantryStatus());
            ledLcdViewer.showCellarResult(storageController.checkCellarStatus());
            ledLcdViewer.showFreezerResult(storageController.checkFreezerStatus());
            ledLcdViewer.showFridgeResult(storageController.checkFridgeStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getBarcodeAndRemove() {
        try {
            storageController.removeElement(barcodeGenerator.get().getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
