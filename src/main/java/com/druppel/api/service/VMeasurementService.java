package com.druppel.api.service;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.dal.VMeasurementRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VMeasurementService {
    @Autowired
    private VMeasurementRepo vMeasurementRepo;

    public Map<Date, List<Map<String, Object>>> getAverageSummary(String days, String EspId, String type) {
        List<MeasurementSummary> list = vMeasurementRepo.getAverageSummary(getDateBack(days), Integer.parseInt(EspId), type == null ? "" : type);
        return measurementsGroupedByDay(list);
    }

    public Date getDateBack(String days) {
        Date currentDate = new Date();
        Calendar queryDate = Calendar.getInstance();
        queryDate.setTime(currentDate);
        queryDate.add(Calendar.DATE, -Integer.parseInt(days));
        return queryDate.getTime();
    }

    public Map<Date, List<Map<String, Object>>> measurementsGroupedByDay(List<MeasurementSummary> list) {
        Map<Date, List<Map<String, Object>>> summary = new HashMap<>();
        for (MeasurementSummary measurementSummary : list) {
            ObjectMapper m = new ObjectMapper();
            Map<String, Object> measurementMap = m.convertValue(measurementSummary, Map.class);
            measurementMap.remove("date");
            if (summary.containsKey(measurementSummary.getDate())) {
                List<Map<String, Object>> listSummary = summary.get(measurementSummary.getDate());
                listSummary.add(measurementMap);
            } else {
                List<Map<String, Object>> listSummary = new ArrayList<>();
                listSummary.add(measurementMap);
                summary.put(measurementSummary.getDate(), listSummary);
            }
        }
        return summary;
    }
}