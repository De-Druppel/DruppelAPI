package com.druppel.api.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class DatabaseToApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void getMeasurementsFromDatabaseWithOnlyMockingRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/druppel-api/past-readings/")
                .accept(MediaType.APPLICATION_JSON)
                .param("api-key", "DRUPPEL_KEY")
                .param("days", "60")
                .param("esp-id", "123123")
                .param("type", "temperature")
                .accept(MediaType.APPLICATION_JSON)
                ;
        ;
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
        // Test
        String expected = "{\"code\":200,\"message\":\"ok\",\"data\":{\"2020-10-26T23:00:00.000+00:00\":" +
                "[{\"value\":12.32,\"type\":\"temperature\"}],\"2020-10-27T23:00:00.000+00:00\":" +
                "[{\"value\":12.32,\"type\":\"temperature\"}]}}";

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

    }
}
