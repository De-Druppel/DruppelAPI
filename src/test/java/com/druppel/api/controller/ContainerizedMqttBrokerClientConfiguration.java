package com.druppel.api.controller;

import com.hivemq.testcontainer.junit4.HiveMQTestContainerRule;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * USAGE:
 * Download/run Docker
 * Add to test environment variables: MQTT_TOPIC=Garden/+/Measurement/#;CLIENT_ID=testPublisher
 */
@TestConfiguration
@Profile("ContainerizedMqttBroker")
public class ContainerizedMqttBrokerClientConfiguration {

@Autowired
    ApplicationContext applicationContext;

    @Bean
    public HiveMQTestContainerRule hiveMQTestContainerRule() {
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


}
