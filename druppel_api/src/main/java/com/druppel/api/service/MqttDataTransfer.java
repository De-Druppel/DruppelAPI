package com.druppel.api.service;

import com.druppel.api.model.Plant;

public class MqttDataTransfer {
    MqttParser mqttParser = new MqttParser();
    Plant plant;

    public void saveMeasurement(String mqttHeader, String mqttPayload) {
        plant = mqttParser.parse(mqttHeader, mqttPayload);
    }
}
