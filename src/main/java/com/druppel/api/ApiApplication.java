package com.druppel.api;

import com.druppel.api.service.MqttDataTransfer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication (scanBasePackages={"com.druppel.api"})
@ComponentScan(basePackages ={"com.druppel.api.controller","com.druppel.api.service","com.druppel.api.model","com.druppel.api.dal"})

public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);

        //Testcode. Feel free to remove.
        MqttDataTransfer mqttDataTransfer = new MqttDataTransfer();
        mqttDataTransfer.saveMeasurement("{mqtt_receivedRetained=false, mqtt_id=1, mqtt_duplicate=false, id=7e7636cc-7d70-f965-d55d-0455bac6160c, mqtt_receivedTopic=Garden/12345678/Moisture, mqtt_receivedQos=1, timestamp=1603137676389}", "65.21");
    }
}
