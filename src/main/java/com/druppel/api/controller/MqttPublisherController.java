package com.druppel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class MqttPublisherController {
    private final boolean DEBUG = true;

    private final ApplicationContext context;
    private MqttTestPublisherConfiguration.MqttMessageProducer mqttMessageProducer;

    @Autowired
    public MqttPublisherController(ApplicationContext context) {
        this.context = context;
        mqttMessageProducer = this.context.getBean(MqttTestPublisherConfiguration.MqttMessageProducer.class);

        if (DEBUG) {
            System.out.println("DEBUG enabled in class: " + this.getClass());
            startMqttTestPublisher();
        }
    }

    public void startMqttTestPublisher() {
        while(true) {
            try {
                mqttMessageProducer.sendToMqtt("foo " + System.currentTimeMillis());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
