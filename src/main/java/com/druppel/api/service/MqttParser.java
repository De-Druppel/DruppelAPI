package com.druppel.api.service;

import com.druppel.api.model.Measurement;
import com.druppel.api.model.Plant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class is responsible for pass a MQTT message header String and playload into a function so as to parse the relevant
 * data into a Plant object and its Measurement sub-object, and returning the Plant.
 */

@Component
public class MqttParser {
    private final boolean DEBUG = true;
    private String topicString;

    /**
     * Takes a MQTT header String and MQTT payload String. Parses data from these strings, constructs and returns a Plant object with
     * a Measurement sub-object.
     *
     * MQTT Header example: {mqtt_receivedRetained=false, mqtt_id=0, mqtt_duplicate=false, id=f4459eeb-f88a-46a3-af3d-9dc7521aac40, mqtt_receivedTopic=Garden/83209475/Measurement/Temperature, mqtt_receivedQos=0, timestamp=1605259492850}
     *MQTT Payload example: 33.2
     *
     * @param mqttHeader String MQTT message header
     * @param mqttPayload String MQTT message payload
     * @return Plant object with associated Measurement sub-object in single item List
     */
    public Plant parse(String mqttHeader, String mqttPayload) {

        Plant plant = new Plant();
        plant.setMeasurementList(new ArrayList<>());
        Measurement measurement = new Measurement();

//        Find the topic substring in the header string.
        topicString = mqttHeader.substring(mqttHeader.indexOf("Garden/"));
        topicString = topicString.substring(0, topicString.indexOf(", mqtt_receivedQos"));

//        Split topic string by /.
        String[] topicStringArray = topicString.split("/", 0);

//        Constructing Plant and measurements object from MQTT message data.
        plant.setIdEsp(parseEspIdFromTopic(topicStringArray));
        measurement.setType(parseTypeFromTopic(topicStringArray));
        measurement.setValue(parseMeasurementValueFromPayload(mqttPayload));
        measurement.setDate(new Date());
        plant.getMeasurementList().add(measurement);
        measurement.setPlant(plant);

        if (DEBUG) {
            System.out.println("DEBUG enabled in class: " + this.getClass());
            debug(plant);
        }

        return plant;
    }

    /**
     * Extracts the Esp-Id from a MQTT topic String
     *
     * @param topicStringArray String array of MQTT topics extracted from MQTT message header
     * @return int Esp-Id of the device that published the MQTT message
     */
    private int parseEspIdFromTopic(String[] topicStringArray) {
        //Find the espId substring in the topic string.
        String espIdString = topicStringArray[1];
        return Integer.parseInt(espIdString);
    }

    /**
     * Extracts the measurement type from a MQTT topic String
     *
     * @param topicStringArray String array of MQTT topics extracted from MQTT message header
     * @return String type of the measurement that was published
     */
    private String parseTypeFromTopic(String[] topicStringArray) {
        //Find the measurement type substring in the topic string.
        String typeString = topicStringArray[3];
        return typeString;
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
        System.out.println();
        System.out.println("Parse values of " + this.toString() + ":");
        System.out.println("Topic: " + topicString);
        System.out.println("EspId: " + plant.getIdEsp());
        System.out.println("Measurement type: " + measurement.getType());
        System.out.println("Measurement value: " + measurement.getValue());
        System.out.println("Current local date/time: " + measurement.getDate());
    }
}
