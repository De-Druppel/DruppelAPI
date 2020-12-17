package com.druppel.api.controller;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.dal.MeasurementSummaryImpl;
import com.druppel.api.dal.VMeasurementRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTestingThroughAllLayers {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VMeasurementRepo vMeasurementRepo;
    @MockBean
    private com.druppel.api.dal.MeasurementRepo MeasurementRepo;

    @Test
    public void getMeasurementThroughAllLayers() throws Exception {
        // Arrange
        String expectedJson = "{\"code\":200,\"message\":\"ok\",\"data\":{\"2020-10-26T00:00:00.000+00:00\":" +
                "[{\"value\":12.32,\"type\":\"temperature\"}]," +
                "\"2020-10-27T00:00:00.000+00:00\":[{\"value\":15.32,\"type\":\"temperature\"}]}}";

        List<MeasurementSummary> summaryList = new ArrayList<>();
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat1.setTimeZone(tz);
        Date date1 = dateFormat1.parse("26/10/2020");
        Date date2 = dateFormat1.parse("27/10/2020");
        MeasurementSummary summary1 = new MeasurementSummaryImpl(date1, "temperature", (float) 12.32);
        summaryList.add(summary1);
        MeasurementSummary summary2 = new MeasurementSummaryImpl(date2, "temperature", (float) 15.32);
        summaryList.add(summary2);
        //Act
        when(vMeasurementRepo.getAverageSummary(any(Date.class),anyInt(),anyString())).thenReturn(summaryList);

        this.mockMvc.perform(get("/druppel-api/past-readings/")
                .accept(MediaType.APPLICATION_JSON)
                .param("api-key", "DRUPPEL_KEY")
                .param("days", "30")
                .param("esp-id", "123123")
                .param("type", "temperature"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(expectedJson))
        ;
        //Assert
        verify(vMeasurementRepo, times(1)).getAverageSummary(any(Date.class),anyInt(),anyString());

    }
}
