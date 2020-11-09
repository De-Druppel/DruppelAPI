package com.druppel.api.service;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.dal.VMeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class VMeasurementService {
    @Autowired
    private VMeasurementRepo vMeasurementRepo;

    public List<MeasurementSummary> getAverageSummary(String days, String EspId, String type){

        Date currentDate = new Date();
        Calendar queryDate = Calendar.getInstance();
        queryDate.setTime(currentDate);
        queryDate.add(Calendar.DATE, -Integer.parseInt(days));

        return vMeasurementRepo.getAverageSummary(queryDate.getTime(), Integer.parseInt(EspId), type);
    }
}
