package com.druppel.api.service;

import com.druppel.api.model.Plant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MqttParserTest {

    // Create a parameterized test
    @Parameterized.Parameters(name = "{index}: expected: {0}, payload: {1} header: {2}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true, "33.2", "{mqtt_receivedRetained=false, mqtt_id=0, mqtt_duplicate=false, id=f4459eeb-f88a-46a3-af3d-9dc7521aac40, mqtt_receivedTopic=Garden/83209475/Measurement/Temperature, mqtt_receivedQos=0, timestamp=1605259492850}"},
                {false, "25.4", "{mqtt_receivedRetained=false, mqtt_id=0, mqtt_duplicate=false, id=f4459eeb-f88a, mqtt_receivedTopic=Garden/Measurement/Temperature/83209475, mqtt_receivedQos=0, timestamp=1605259492850}"},
        });
    }

    private final boolean expected;
    private final String payload;
    private final String header;

    public MqttParserTest(boolean expected, String payload, String header) {
        this.expected = expected;
        this.payload = payload;
        this.header = header;
    }

    @Test
    public void parseMqttData() {
        //Arrange
        MqttParser parser = new MqttParser();

        //Act
        boolean result = true;
        try {
            parser.parse(this.header, this.payload);
        }
        catch(Exception e) {
            result = false;
        }

        //assert
        assertEquals(this.expected, result);
    }

}