//TODO: class desciption
package com.druppel.api.service;
import com.druppel.api.model.Plant;

public class MqttDataTransfer {
    MqttParser mqttParser = new MqttParser();

    //TODO: method description
    public void saveMeasurement(String mqttHeader, String mqttPayload) {
        Plant plant = mqttParser.parse(mqttHeader, mqttPayload);
    }
}
