package ch.supsi.sfs.frontend.controller;

import ch.supsi.sfs.backend.controller.StorageController;
import ch.suspi.simulator.sensors.analog.light.LightSensorSimulator;
import ch.suspi.simulator.sensors.analog.loadcell.LoadCellSimulator;
import ch.suspi.simulator.sensors.barcode.random.RandomBarcodeSimulator;
import ch.suspi.simulator.sensors.digital.temphumidity.TemperatureAndHumiditySimulator;
import org.iot.raspberry.grovepi.sensors.synch.SensorMonitor;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static ch.supsi.sfs.demo.DemoTest.GROVE_PI;

public class StationController {
    private static StationController instance;
    private final StorageController storageController;
    private RandomBarcodeSimulator barcodeGenerator;
    private SensorMonitor<Double> weigher;
    private LightSensorSimulator cellarLight;
    private final TemperatureAndHumiditySimulator temperatureFrizzer;
    private final TemperatureAndHumiditySimulator temperatureFridge;

    private final LedLcdViewer ledLcdViewer;

    private StationController(){
        ledLcdViewer = LedLcdViewer.getInstance();
        storageController=StorageController.getInstance();
        temperatureFridge=new TemperatureAndHumiditySimulator(GROVE_PI, 1, TemperatureAndHumiditySimulator.Type.DHT11);
        temperatureFrizzer=new TemperatureAndHumiditySimulator(GROVE_PI, 2, TemperatureAndHumiditySimulator.Type.DHT21);
        try {
            cellarLight=new LightSensorSimulator(GROVE_PI, 3, "Cellar Light Random Generator");
            weigher=new SensorMonitor<Double>(new LoadCellSimulator(GROVE_PI, 6, "Weigher"), 200);
            barcodeGenerator=new RandomBarcodeSimulator("Barcode Generator");
        } catch (IOException e) {
            e.printStackTrace();
        }
        weigher.start();
    }

    public void genBarcodeStatusAndSave(){
        try {
            if(ThreadLocalRandom.current().nextInt(0, 11)%2==0) {
                storageController.setCellarLight(cellarLight.get());
                storageController.setFreezerTemp(temperatureFrizzer.get().getTemperature());
                storageController.setFridgeTemp(temperatureFridge.get().getTemperature());
            }
            storageController.saveProduct(barcodeGenerator.get().getText(), weigher.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StationController getInstance() {
        return instance==null?(instance=new StationController()):instance;
    }

    public double getWeigherValue(){
        return weigher.getValue();
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
