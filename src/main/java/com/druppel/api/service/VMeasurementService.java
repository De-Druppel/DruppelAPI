package com.druppel.api.service;

import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.dal.VMeasurementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VMeasurementService {
    @Autowired
    private VMeasurementRepo vMeasurementRepo;

    public List<MeasurementSummary> getAverageSummary(int days, int EspId, String type){
        return vMeasurementRepo.getAverageSummary(days, EspId, type);
    }
}
