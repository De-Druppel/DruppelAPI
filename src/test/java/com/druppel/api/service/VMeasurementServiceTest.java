package com.druppel.api.service;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.dal.MeasurementSummaryImpl;
import com.druppel.api.dal.VMeasurementRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@WebMvcTest(value = VMeasurementService.class)
public class VMeasurementServiceTest {
    @MockBean
    private VMeasurementRepo vMeasurementRepo;
    @InjectMocks
    private VMeasurementService vMeasurementService;
    @MockBean
    private RestDataTransfer restDataTransfer;

    @Test
    public void getDateBackReturnsDateTenDaysAgo() throws ParseException {
//
        //Arrange
        Calendar expected = Calendar.getInstance();
        expected.setTime(new Date());
        expected.add(Calendar.DATE, -10);

        //Act
        Date actual = vMeasurementService.getDateBack("10");
        //assert
        assertEquals(expected.getTime(), actual);
    }

    @Test
    public void groupMeasurementsByDateTest() throws Exception {
        // Arrange
        Map<Date, List<Map<String, Object>>> expectedList = new HashMap<>();
        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2020");
        Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse("28/10/2020");

        List<Map<String, Object>> list1 = new ArrayList<>();
        List<Map<String, Object>> list2 = new ArrayList<>();

        Map<String, Object> subMap1 = new HashMap<>();
        subMap1.put("type", "temperature");
        subMap1.put("value", 12.32);

        Map<String, Object> subMap2 = new HashMap<>();
        subMap2.put("type", "temperature");
        subMap2.put("value", 15.32);

        list1.add(subMap1);
        list2.add(subMap2);

        expectedList.put(date1, list1);
        expectedList.put(date2, list2);

        List<MeasurementSummary> summaryList = new ArrayList<>();
        Date date11 = new SimpleDateFormat("dd/MM/yyyy").parse("27/10/2020");
        MeasurementSummary summary1 = new MeasurementSummaryImpl(date11, "temperature", (float) 12.32);
        summaryList.add(summary1);
        Date date22 = new SimpleDateFormat("dd/MM/yyyy").parse("28/10/2020");
        MeasurementSummary summary2 = new MeasurementSummaryImpl(date22, "temperature", (float) 15.32);
        summaryList.add(summary2);


        // Act
        Map<Date, List<Map<String, Object>>> actualList = vMeasurementService.groupMeasurementsByDate(summaryList);

        boolean expected = true;
        List<Map<String, Object>> subMapExpected;
        List<Map<String, Object>> subMapActual;
        if (expectedList.size() != actualList.size())
            expected = false;
        for (Date key : expectedList.keySet()) {
            subMapExpected = expectedList.get(key);
            subMapActual = actualList.get(key);
            for (int i = 0; i < subMapExpected.size(); i++) {
                for (String keyS : subMapExpected.get(i).keySet()) {
                    expected = subMapExpected.get(i).keySet().equals(subMapActual.get(i).keySet());

                }
            }
        }

        //Assert
        assertTrue(expected);

    }
}
