package com.druppel.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication (scanBasePackages={"com.druppel.api"})
@ComponentScan(basePackages ={"com.druppel.api.controller","com.druppel.api.service","com.druppel.api.model","com.druppel.api.dal"})

public class ApiApplication {

    AnnotationConfigApplicationContext applicationContext;
    public static void main(String[] args) {
        //ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MqttClientConfiguration.class, MqttTestPublisherConfiguration.class, MqttClientController.class, RestApiController.class);
        //System.out.println("APP CONTEXT: " + applicationContext);
        SpringApplication.run(ApiApplication.class, args);
    }


}
