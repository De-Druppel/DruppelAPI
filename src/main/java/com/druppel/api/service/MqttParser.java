package com.druppel.api.service;

import com.druppel.api.model.Measurement;
import com.druppel.api.model.Plant;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class is responsible for pass a MQTT message header String and playload into a function so as to parse the relevant
 * data into a Plant object and its Measurement sub-object, and returning the Plant.
 */
public class MqttParser {
    private final boolean DEBUG = true;

    /**
     * Takes a MQTT header String and MQTT payload String. Parses data from these strings, constructs and returns a Plant object with
     * a Measurement sub-object.
     *
     * @param mqttHeader String MQTT message header
     * @param mqttPayload String MQTT message payload
     * @return Plant object with associated Measurement sub-object in single item List
     */
    public Plant parse(String mqttHeader, String mqttPayload) {
        Plant plant = new Plant();
        plant.setMeasurementList(new ArrayList<>());
        Measurement measurement = new Measurement();

        //Find the topic substring in the header string.
        String topicString = mqttHeader.substring(mqttHeader.indexOf("Garden/" + 1));
        topicString = topicString.substring(0, topicString.indexOf(", mqtt_receivedQos"));

        //Constructing Plant and measurements object from MQTT message data.
        plant.setIdEsp(parseEspIdFromTopic(topicString));
        measurement.setType(parseTypeFromTopic(topicString));
        measurement.setValue(parseMeasurementValueFromPayload(mqttPayload));
        measurement.setDate(new Date());
        plant.getMeasurementList().add(measurement);

        if (DEBUG) {
            debug(plant);
        }

        return plant;
    }

    /**
     * Extracts the Esp-Id from a MQTT topic String
     *
     * @param topicString String of MQTT topics extracted from MQTT message header
     * @return int Esp-Id of the device that published the MQTT message
     */
    private int parseEspIdFromTopic(String topicString) {
        //Find the espId substring in the topic string.
        String espIdString = topicString.substring(topicString.indexOf("/") + 1);
        espIdString = espIdString.substring(0, topicString.indexOf("/"));
        return Integer.parseInt(espIdString);
    }

    /**
     * Extracts the measurement type from a MQTT topic String
     *
     * @param topicString String of MQTT topics extracted from MQTT message header
     * @return String type of the measurement that was published
     */
    private String parseTypeFromTopic(String topicString) {
        //Find the measurement type substring in the topic string.
        return topicString.substring(topicString.indexOf("/", topicString.indexOf("/") + 1) + 1);
    }

    /**
     * Returns the value of the telemetry data included in a MQTT message playload
     *
     * @param mqttPayload String MQTT message payload of telemetry reading value
     * @return float Double decimal precision of a telemetry reading value
     */
    private float parseMeasurementValueFromPayload(String mqttPayload) {
        return Float.parseFloat(mqttPayload);
    }

    /**
     * Constructed Plant and associated Measurement data by parse method printed to console for debugging purposes
     *
     * @param plant Plant object constructed by parse method
     */
    private void debug(Plant plant) {
        Measurement measurement = plant.getMeasurementList().get(0);
        System.out.println("Parse values of " + this.toString() + ":");
        System.out.println("EspId: " + plant.getIdEsp());
        System.out.println("Measurement type: " + measurement.getType());
        System.out.println("Measurement value: " + measurement.getValue());
        System.out.println("Current local date/time: " + measurement.getDate());
    }
}
