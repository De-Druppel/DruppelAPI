package com.druppel.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Import(ContainerizedMqttBrokerClientConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("ContainerizedMqttBroker")

public class DatabaseIntegrationTest {
    @Autowired
    ApplicationContext applicationContext;


    public DatabaseIntegrationTest() {

    }

    @Test
    public void printContext() {
        System.out.println(applicationContext.getBean(MqttPahoClientFactory.class).getConnectionOptions().getServerURIs()[0]);
    }

}
