//TODO: class desciption
package com.druppel.api.service;
import com.druppel.api.dal.MeasurementRepo;
import com.druppel.api.dal.PlantRepo;
import com.druppel.api.model.Measurement;
import com.druppel.api.model.Plant;
import org.springframework.stereotype.Service;

@Service
public class MqttDataTransfer {
    private final boolean DEBUG = true;
    private final MqttParser mqttParser;
    private final PlantRepo plantRepo;
    private final MeasurementRepo measurementRepo;

    public MqttDataTransfer(PlantRepo plantRepo, MeasurementRepo measurementRepo, MqttParser mqttParser) {
        this.plantRepo = plantRepo;
        this.measurementRepo = measurementRepo;
        this.mqttParser = mqttParser;

        if (DEBUG) {
            System.out.println("Debug enabled in class: " + this.getClass());
        }
    }

    //TODO: method description
    public void saveMeasurement(String mqttHeader, String mqttPayload) {
        Plant plant = mqttParser.parse(mqttHeader, mqttPayload);
        Measurement measurement = plant.getMeasurementList().get(0);

        //Checks to see if plant with Esp ID exists. If not, save new plant object with associated measurement.
        Plant checkPlant = plantRepo.findByIdEsp(plant.getIdEsp());
        if (checkPlant == null) {
            plantRepo.save(plant);
        } else if (checkPlant != null) {
            //If plant with Esp ID already exists, set Plant entity in Measurement entity and save measurement.
            plant = checkPlant;
            measurement.setPlant(plant);
            measurementRepo.save(measurement);
        }

        if (DEBUG) {
            debug(plant, measurement);
        }
    }

    private void debug(Plant plant, Measurement measurement) {
        System.out.println();
        System.out.println("Saved values of " + this.toString() + ":");
        System.out.println("EspId: " + plant.getIdEsp());
        System.out.println("Measurement type: " + measurement.getType());
        System.out.println("Measurement value: " + measurement.getValue());
        System.out.println("Current local date/time: " + measurement.getDate());
    }
}
