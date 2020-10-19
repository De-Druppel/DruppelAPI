

package com.druppel.api.service;

import com.druppel.api.model.Plant;
import java.time.LocalDateTime;

public class MqttParser {
    private final boolean DEBUG = true;

    private Plant plantMeasurement = new Plant();

    public Plant parse(String mqttHeader, String mqttPayload) {
        int espId;
        String type;
        float value;
        LocalDateTime dateTime;

        //Find the topic substring in the header string.
        String topicString = mqttHeader.substring(mqttHeader.indexOf("Garden/" + 1));
        topicString = topicString.substring(0, topicString.indexOf(", mqtt_receivedQos"));

        espId = parseEspIdFromTopic(topicString);
        type = parseTypeFromTopic(topicString);
        value = parseMeasurementValueFromPayload(mqttPayload);
        dateTime = LocalDateTime.now();

        //TODO: feed Id, type, value, dateTime into Plant model object; need model object implementation.

        if (DEBUG) {
            System.out.println("Parse values of " + this.toString() + ":");
            System.out.println("EspId: " + espId);
            System.out.println("Measurement type: " + type);
            System.out.println("Measurement value: " + value);
            System.out.println("Current local date/time: " + dateTime);
        }

        return plantMeasurement;
    }

    //TODO: Method descriptions.
    private int parseEspIdFromTopic(String topicString) {
        //Find the espId substring in the topic string.
        String espIdString = topicString.substring(topicString.indexOf("/") + 1);
        espIdString = espIdString.substring(0, topicString.indexOf("/"));
        return Integer.parseInt(espIdString);
    }

    private String parseTypeFromTopic(String topicString) {
        //Find the measurement type substring in the topic string.
        return topicString.substring(topicString.indexOf("/", topicString.indexOf("/") + 1) + 1);
    }

    private float parseMeasurementValueFromPayload(String mqttPayload) {
        return Float.parseFloat(mqttPayload);
    }
}
