package com.druppel.api.controller;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import java.io.IOException;
import java.util.Properties;

/**
 * This class is responsible for setting up spring IoC container beans regarding MQTT client configuration.
 */

@Configuration
public class MqttClientConfiguration {
    private final ApplicationContext context;

    public MqttClientConfiguration(ApplicationContext context) {
        this.context = context;
    }

    /**
     * @return subscribable message channel for single subscriber.
     */
    @Bean
    public DirectChannel mqttInputChannel() {
        return new DirectChannel() {
        };
    }

    /**
     * @return MQTT subscriber details properties from properties file.
     */
//    @Bean
//    public Properties properties() {
//        Properties properties = new Properties();
//        try {
//            properties.load(MqttClientConfiguration.class.getClassLoader().getResourceAsStream("mqttclient.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return properties;
//    }

    /**
     * Sets connection options for the Mqtt Client factory.
     *
     * @return mqtt client factory.
     */
    @Bean
    @Profile("production")
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        // Set mqtt connection options
        options.setServerURIs(new String[]{System.getenv("SERVERURL")});
        options.setUserName(System.getenv("USERNAME"));
        options.setPassword(System.getenv("PASSWORD").toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * Creates and sets up adapter for Mqtt and Messagechannel endpoints.
     *
     * @return inbound message producer.
     */
    @Bean
    public MessageProducer inbound() {
        //Generate unique clientId.
        String clientId = System.getenv("CLIENT_ID") + System.currentTimeMillis();
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory(), System.getenv(("MQTT_TOPIC")));
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(context.getBean("mqttInputChannel", MessageChannel.class));
        return adapter;
    }
}
