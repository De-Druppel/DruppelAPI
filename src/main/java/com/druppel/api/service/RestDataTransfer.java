package com.druppel.api.service;

import com.druppel.api.dal.MeasurementRepo;
import com.druppel.api.dal.MeasurementSummary;
import com.druppel.api.model.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RestDataTransfer {
    @Autowired
    private MeasurementRepo measurementRepo;

    public void save(Measurement measurement) {
        measurementRepo.save(measurement);
    }

    public List<Measurement> findAll(){
        return (List<Measurement>) measurementRepo.findAll();
    }

    public List<MeasurementSummary> getAverageSummary(Date date1, Date date2,int EspId){
       return measurementRepo.getAverageSummary(date1,date2,EspId);
    }

}
