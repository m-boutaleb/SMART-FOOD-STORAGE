package main;

import ch.industry4_0.influx.connector.InfluxConnector;
import ch.suspi.simulator.grove.GrovePiSimulator;
import ch.suspi.simulator.sensors.digital.button.ButtonSimulator;
import ch.suspi.simulator.sensors.digital.led.LedSimulator;
import ch.suspi.simulator.sensors.digital.ranger.UltrasonicRangerSimulator;
import ch.suspi.simulator.sensors.digital.temphumidity.TemperatureAndHumiditySimulator;
import java.util.logging.Level;
import java.util.logging.Logger;

import service.ProductService;
import model.product.type.AboveZeroProduct;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.sensors.i2c.GroveRgbLcd;
import org.iot.raspberry.grovepi.sensors.synch.SensorMonitor;
import service.implementation.ProductServiceImpl;

public class Test {
    public static void main(String[] args) throws Exception {
        GrovePi grovePi =initLoggerAndGetGrovePi();
        InfluxConnector ic= InfluxConnector.getInstance();

        CellarController

        LedSimulator emergencyLightRed = new LedSimulator(grovePi,3);
        LedSimulator okLightGreen = new LedSimulator(grovePi,2);
        okLightGreen.set(true);

        ButtonSimulator emergencyStop=new ButtonSimulator(grovePi, 7);

        UltrasonicRangerSimulator rangerEntrance=new UltrasonicRangerSimulator(grovePi, 5);
        SensorMonitor<Double> rangerEntranceSM = new SensorMonitor(rangerEntrance, 200);

        UltrasonicRangerSimulator rangerExit=new UltrasonicRangerSimulator(grovePi, 4);
        SensorMonitor<Double> rangerExitSM = new SensorMonitor(rangerExit, 200);

        TemperatureAndHumiditySimulator temperatureHumidityFrizzer=new TemperatureAndHumiditySimulator(grovePi, 6, TemperatureAndHumiditySimulator.Type.DHT11);
        TemperatureAndHumiditySimulator temperatureHumidityFridge=new TemperatureAndHumiditySimulator(grovePi, 6, TemperatureAndHumiditySimulator.Type.DHT11);

        GroveRgbLcd lcdFreezer= grovePi.getLCD();
        GroveRgbLcd lcdFridge= grovePi.getLCD();
        GroveRgbLcd lcdCellar= grovePi.getLCD();


        lcdFreezer.setRGB(255,0,0);
        lcdFridge.setRGB(255,0,0);
        lcdCellar.setRGB(255,0,0);









        ProductService productService= ProductServiceImpl.getInstance();
        productService.productByBarcodeAndSave(32451525L, 241.2);


        System.out.println(new AboveZeroProduct(35413413L, "Pasta al sugo", 2341,AboveZeroProduct.class.getName()));




        ic.close();
    }

    private static GrovePi initLoggerAndGetGrovePi() {
        Logger.getLogger("GrovePi").setLevel(Level.WARNING);
        Logger.getLogger("RaspberryPi").setLevel(Level.WARNING);
        return  new GrovePiSimulator();
    }
}
