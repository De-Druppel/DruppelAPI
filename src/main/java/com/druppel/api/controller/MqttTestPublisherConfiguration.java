package com.druppel.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * This class is responsible for setting up spring IoC container beans regarding MQTT publisher configuration.
 */
@Configuration
public class MqttTestPublisherConfiguration {

    private final ApplicationContext context;


    @Autowired
    public MqttTestPublisherConfiguration(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        String clientId = "testPublish" + System.currentTimeMillis();
        MqttPahoClientFactory mqttClientFactory = context.getBean("mqttClientFactory", MqttPahoClientFactory.class);
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(clientId, mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("Garden/12345678/Moisture");
        return messageHandler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.subscribe(mqttOutbound());
        return directChannel;
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttMessageProducer {

        void sendToMqtt(String data);
    }
}
