package com.druppel.api.controller;

import com.druppel.api.dal.MeasurementRepo;
import com.druppel.api.dal.PlantRepo;
import com.druppel.api.model.Measurement;
import com.druppel.api.model.Plant;
import com.druppel.api.service.VMeasurementService;
import com.hivemq.testcontainer.junit4.HiveMQTestContainerRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@Import(ContainerizedMqttBrokerClientConfiguration.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"ContainerizedMqttBroker"})

//Testtopic: Garden/[generatedEspId]/Measurement/Test
//Test value: 33.65

public class DatabaseIntegrationTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private PlantRepo plantRepo;
    @Autowired
    private MeasurementRepo measurementRepo;
    private final float MEASUREMENT_VALUE = (float) 33.65;
    @Autowired
    private int generatedEspId;
    @Autowired VMeasurementService vMeasurementService;
    @Autowired
    @Rule
    public HiveMQTestContainerRule hiveMQTestContainerRule;

    private List<Measurement> measurementList;
    Plant checkPlant;

    @Test
    public void DatabaseIntegrationTest() {
        int testedEspId = checkPlant.getIdEsp();
        Measurement testedMeasurement = measurementList.get(0);

        Assert.assertEquals(testedEspId, generatedEspId);
        Assert.assertEquals(testedMeasurement.getType(), "Test");
        Assert.assertEquals(testedMeasurement.getValue(), MEASUREMENT_VALUE, 0.009);
    }

    /**
     * Publishes message to containerized MQTT broker.
     */
    @Before
    public void sendTestMessageToMqtt() {
        //Get mqtt message gateway from ContainerizedMqttBrokerClientConfiguration.
        ContainerizedMqttBrokerClientConfiguration.MqttMessageProducer mqttMessageGateway = applicationContext.getBean(ContainerizedMqttBrokerClientConfiguration.MqttMessageProducer.class);
        mqttMessageGateway.sendToMqtt(String.valueOf(MEASUREMENT_VALUE));

        findResultInDatabase();
    }

    /**
     * Uses the plant repo to find a plant object with ESP-ID matching the generatedEspId.
     */
    private void findResultInDatabase() {
        for (int i = 0; i < 10; i++) {
            checkPlant = plantRepo.findByIdEsp(generatedEspId);

            //Checks if the plant has been fetched from the database.
            if (checkPlant == null) {
                System.out.println("No plant returned by database. Retrying.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Checks if the Measurement object associated with the plant has been fetched from the database.
            } else if (checkPlant.getMeasurementList().size() == 0){
                System.out.println("No plant returned by database. Retrying.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }
        measurementList = checkPlant.getMeasurementList();
    }

}
