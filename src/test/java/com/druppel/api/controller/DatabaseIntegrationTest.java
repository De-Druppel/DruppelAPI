package com.druppel.api.controller;

import com.hivemq.testcontainer.junit4.HiveMQTestContainerRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@Import(ContainerizedMqttBrokerClientConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ContainerizedMqttBroker")

public class DatabaseIntegrationTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    @Rule
    public HiveMQTestContainerRule hiveMQTestContainerRule;

    @Test
    public void DatabaseIntegrationTest() {
        ContainerizedMqttBrokerClientConfiguration.MqttMessageProducer mqttMessageGateway = applicationContext.getBean(ContainerizedMqttBrokerClientConfiguration.MqttMessageProducer.class);
        mqttMessageGateway.sendToMqtt("33.65");
    }

    @Test
    public void printContext() {
        System.out.println(applicationContext.getBean(MqttPahoClientFactory.class).getConnectionOptions().getServerURIs()[0]);
        System.out.println(hiveMQTestContainerRule.toString());
    }

}
