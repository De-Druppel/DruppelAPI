package com.druppel.api.controller;

import com.druppel.api.service.MqttDataTransfer;
import com.druppel.api.service.RestDataTransfer;
import com.druppel.api.service.VMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RestApiController.class)
@ExtendWith(SpringExtension.class)
public class RestApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VMeasurementService vMeasurementService;
    @MockBean
    private RestDataTransfer restDataTransfer;
    @MockBean
    private MqttDataTransfer mqttDataTransfer;

    @Test
    public void getMeasurementsTestSucces() throws Exception {

        Map<Date, List<Map<String, Object>>> infoList = new HashMap<>();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat1.setTimeZone(tz);
        Date date1 = dateFormat1.parse("26/10/2020");

        Date date2 = dateFormat1.parse("27/10/2020");


        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();

        Map<String, Object> subMap1 = new HashMap<>();
        subMap1.put("value", 12.32);
        subMap1.put("type", "temperature");

        Map<String, Object> subMap2 = new HashMap<>();
        subMap2.put("value", 15.32);
        subMap2.put("type", "humidity");
        list1.add(subMap1);
        list2.add(subMap2);

        infoList.put(date1, list1);
        infoList.put(date2, list2);


        // Mock Service
        Mockito.when(vMeasurementService.getAverageSummary("45", "123123", "")).thenReturn(infoList);


        // Mock GET request

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/druppel-api/past-readings/")
                .accept(MediaType.APPLICATION_JSON)
                .param("api-key", "DRUPPEL_KEY")
                .param("days", "45")
                .param("esp-id", "123123")
                .param("type", "")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(infoList));
        ;
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        // Test
        String expected = "{\"code\":200,\"message\":\"ok\",\"data\":{\"2020-10-26T00:00:00.000+00:00\":" +
                "[{\"value\":12.32,\"type\":\"temperature\"}]," +
                "\"2020-10-27T00:00:00.000+00:00\":[{\"value\":15.32,\"type\":\"humidity\"}]}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);


    }

    @Test
    @DisplayName(" calling with wrong API Key should return bad request")
    public void callingWithWrongAPIShouldReturnBadRequest() throws Exception {
        Map<Date, List<Map<String, Object>>> infoList = new HashMap<>();

        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("26/10/2020");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2020");
        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();

        Map<String, Object> subMap1 = new HashMap<>();
        subMap1.put("value", 12.32);
        subMap1.put("type", "temperature");

        Map<String, Object> subMap2 = new HashMap<>();
        subMap2.put("value", 15.32);
        subMap2.put("type", "humidity");
        list1.add(subMap1);
        list2.add(subMap2);

        infoList.put(date1, list1);
        infoList.put(date2, list2);

        Mockito.when(vMeasurementService.getAverageSummary("45", "123123", "")).thenReturn(infoList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/druppel-api/past-readings/")
                .accept(MediaType.APPLICATION_JSON)
                .param("api-key", "DRUPPEL_KEYs")
                .param("days", "45")
                .param("esp-id", "123123")
                .param("type", "")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(infoList));

        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

        assertEquals (500,result.getResponse().getStatus());

    }
}
