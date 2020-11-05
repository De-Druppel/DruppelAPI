package com.druppel.api.controller;

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
 * Thi class is responsible for handling incloming MQTT messages.
 */
@Controller
@EnableIntegration
public class MqttClientController {

    @Autowired
    ApplicationContext applicationContext;

    public MqttClientController() {
    }

    /**
     * Method that is capable of handling a message.
     * inputChannel: the channel from which this service activator will consume messages.
     */
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            System.out.println(message.getPayload());
            System.out.println(message.getHeaders());
        };
    }

    @Bean
    public EventDrivenConsumer eventDrivenConsumer() {
        DirectChannel mqttInputChannel = applicationContext.getBean("mqttInputChannel", DirectChannel.class);
        return new EventDrivenConsumer(mqttInputChannel, handler());
    }
}
