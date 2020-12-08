package com.druppel.api.controller;

import com.hivemq.testcontainer.junit4.HiveMQTestContainerRule;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * USAGE:
 * Download/run Docker
 * Add to test environment variables: MQTT_TOPIC=Garden/+/Measurement/#;CLIENT_ID=testPublisher
 */
@TestConfiguration
@IntegrationComponentScan
@Profile("ContainerizedMqttBroker")
public class ContainerizedMqttBrokerClientConfiguration {

@Autowired
    ApplicationContext applicationContext;

    //NEEDS to be static final to ensure testcontainer starts before mqtt client and publisher.
    @Bean
    public static final HiveMQTestContainerRule hiveMQTestContainerRule() {
        HiveMQTestContainerRule hiveMQTestContainerRule = new HiveMQTestContainerRule();
        hiveMQTestContainerRule.start();
        return hiveMQTestContainerRule;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        String adress = "tcp://" + hiveMQTestContainerRule().getHost() + ":" + hiveMQTestContainerRule().getFirstMappedPort();
        // Set mqtt connection options
        options.setServerURIs(new String[]{adress});
        factory.setConnectionOptions(options);
        return factory;
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        String clientId = System.getenv("CLIENT_ID") + System.currentTimeMillis();
        MqttPahoClientFactory mqttClientFactory = mqttClientFactory();
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(clientId, mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("Garden/999333558/Measurement/Moisture");
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
