package com.druppel.api;

import com.druppel.api.controller.MqttClientConfiguration;
import com.druppel.api.controller.MqttClientController;
import com.druppel.api.controller.MqttTestPublisherConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiApplication {

    AnnotationConfigApplicationContext applicationContext;
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MqttClientConfiguration.class, MqttTestPublisherConfiguration.class, MqttClientController.class);
        System.out.println("APP CONTEXT: " + applicationContext);
        SpringApplication.run(ApiApplication.class, args);
    }


}
