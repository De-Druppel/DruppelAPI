package com.druppel.api.controller;

import com.druppel.api.service.MqttDataTransfer;
import com.druppel.api.service.MqttParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Controller;

/**
 * This class is responsible for handling incloming MQTT messages.
 */
@Controller
@EnableIntegration
public class MqttClientController {
    final boolean DEBUG = true;
    final ApplicationContext applicationContext;
    private DirectChannel mqttInputChannel;
    private MqttDataTransfer mqttDataTransfer;

    @Autowired
    public MqttClientController(ApplicationContext applicationContext, DirectChannel mqttInputChannel, MqttDataTransfer mqttDataTransfer) {
        this.mqttInputChannel = mqttInputChannel;
        this.applicationContext = applicationContext;
        this.mqttDataTransfer = mqttDataTransfer;
        System.out.println("Debug enabled in class: " + this.getClass());
    }

    /**
     * Method that is capable of handling a message.
     * inputChannel: the channel from which this service activator will consume messages.
     */
    @ServiceActivator
    public MessageHandler handler() {
        return message -> {
            String messageHeader = message.getHeaders().toString();
            String messagePayload = message.getPayload().toString();
            mqttDataTransfer.saveMeasurement(messageHeader, messagePayload);

            if (DEBUG) {
                System.out.println();
                System.out.println("MQTT Message header and playload received by " + this.getClass());
                System.out.println(messageHeader);
                System.out.println(messagePayload);
            }
        };
    }

    @Bean
    public EventDrivenConsumer eventDrivenConsumer() {
        return new EventDrivenConsumer(mqttInputChannel, handler());
    }
}
