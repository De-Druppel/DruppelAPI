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


        //Testcode. Feel free to remove.
//        MqttDataTransfer mqttDataTransfer = new MqttDataTransfer();
//        mqttDataTransfer.saveMeasurement("{mqtt_receivedRetained=false, mqtt_id=1, mqtt_duplicate=false, id=7e7636cc-7d70-f965-d55d-0455bac6160c, mqtt_receivedTopic=Garden/12345678/Moisture, mqtt_receivedQos=1, timestamp=1603137676389}", "65.21");
    }


}
