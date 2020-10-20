//package com.druppel.api.controller;
//
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.annotation.ServiceActivator;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.core.MessageProducer;
//import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
//import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
//import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
//import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.MessageHandler;
//
//import java.io.IOException;
//import java.util.Properties;
//
//@Configuration
//public class MqttApiController {
//
//    private final Properties prop = new Properties();
//
//    /**
//     *
//     */
//    public MqttApiController() {
//        try {
//            // Get mqtt connection properties
//            this.prop.load(MqttApiController.class.getClassLoader().getResourceAsStream("mqtt.properties"));
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    /**
//     * @return
//     */
//    @Bean
//    public MessageChannel mqttInputChannel() {
//        return new DirectChannel();
//    }
//
//    /**
//     * Established connection to mqtt server
//     *
//     * @return mqtt client factory
//     */
//    @Bean
//    public MqttPahoClientFactory mqttClientFactory() {
//        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//        MqttConnectOptions options = new MqttConnectOptions();
//        // Set mqtt connection options
//        options.setServerURIs(new String[]{this.prop.getProperty("SERVERURL")});
//        options.setUserName(this.prop.getProperty("USERNAME"));
//        options.setPassword(this.prop.getProperty("PASSWORD").toCharArray());
//        factory.setConnectionOptions(options);
//        return factory;
//    }
//
//    /**
//     * @return
//     */
//    @Bean
//    public MessageProducer inbound() {
//        MqttPahoMessageDrivenChannelAdapter adapter =
//                new MqttPahoMessageDrivenChannelAdapter(this.prop.getProperty("CLIENT_ID"), mqttClientFactory(), this.prop.getProperty("MQTT_TOPIC"));
//        adapter.setCompletionTimeout(5000);
//        adapter.setConverter(new DefaultPahoMessageConverter());
//        adapter.setQos(1);
//        adapter.setOutputChannel(mqttInputChannel());
//        return adapter;
//    }
//
//    /**
//     * Message handler for all the incoming exceptions
//     *
//     * @return Message
//     */
//    @Bean
//    @ServiceActivator(inputChannel = "mqttInputChannel")
//    public MessageHandler handler() {
//        return message -> {
//            System.out.println(message.getPayload());
//            System.out.println(message.getHeaders());
//        };
//    }
//
//}
