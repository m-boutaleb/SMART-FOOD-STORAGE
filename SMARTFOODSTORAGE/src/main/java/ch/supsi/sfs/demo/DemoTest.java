package ch.supsi.sfs.demo;

import ch.supsi.sfs.backend.database.Database;
import ch.supsi.sfs.frontend.controller.StationController;
import ch.suspi.simulator.grove.GrovePiSimulator;
import ch.suspi.simulator.sensors.digital.button.ButtonSimulator;
import ch.suspi.simulator.sensors.digital.ranger.UltrasonicRangerSimulator;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.listener.GroveDigitalInListener;
import org.iot.raspberry.grovepi.sensors.synch.SensorMonitor;

public class DemoTest {
    public static final GrovePi GROVE_PI =initLoggerAndGetGrovePi();
    public static final double NEW_PRODUCT_DETECTED=20;
    public static final int LATENCY=300;
    public static boolean stationClosed=false;

    public static void main(String[] args) throws Exception {
        final StationController stationController= StationController.getInstance();


        UltrasonicRangerSimulator rangerEntrance=new UltrasonicRangerSimulator(GROVE_PI, 5);
        SensorMonitor<Double> rangerEntranceSM = new SensorMonitor(rangerEntrance, 200);

        UltrasonicRangerSimulator rangerExit=new UltrasonicRangerSimulator(GROVE_PI, 4);
        SensorMonitor<Double> rangerExitSM = new SensorMonitor(rangerExit, 200);

        final ButtonSimulator emergencyStop=new ButtonSimulator(GROVE_PI, 7);

        rangerEntranceSM.start();
        rangerExitSM.start();

        while(!stationClosed){
            double entranceRangerValue=rangerEntrance.get();
            double exitRangerValue=rangerExit.get();
            if(productDetected(entranceRangerValue))
                stationController.genBarcodeStatusAndSave();
            if(productDetected(exitRangerValue))
                stationController.getBarcodeAndRemove();
            checkEmergencyStop(emergencyStop);
            stationController.checkAllRepos();
            Thread.sleep(LATENCY);
        }
        Database.getInstance().closeConnection();
    }

    private static void checkEmergencyStop(final ButtonSimulator emergencyStop) {
        try {
            emergencyStop.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        emergencyStop.setListener(new GroveDigitalInListener() {
            @Override
            public void onChange(final boolean b, final boolean b1) {
                stationClosed=b1;
            }
        });
    }

    private static boolean productDetected(final double rangerValue) {
        return rangerValue<NEW_PRODUCT_DETECTED;
    }

    private static GrovePi initLoggerAndGetGrovePi() {
        Logger.getLogger("GrovePi").setLevel(Level.WARNING);
        Logger.getLogger("RaspberryPi").setLevel(Level.WARNING);
        return  new GrovePiSimulator();
    }
}
