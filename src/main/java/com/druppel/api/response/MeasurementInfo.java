package com.druppel.api.response;

import java.util.Date;

public class MeasurementInfo {
    private Date date;
    private Float averageMoisture;
    private Float averageTemp;

    public MeasurementInfo() {
    }

    public MeasurementInfo(Date date, Float averageMoisture, Float averageTemp) {
        this.date = date;
        this.averageMoisture = averageMoisture;
        this.averageTemp = averageTemp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Float getAverageMoisture() {
        return averageMoisture;
    }

    public void setAverageMoisture(Float averageMoisture) {
        this.averageMoisture = averageMoisture;
    }

    public Float getAverageTemp() {
        return averageTemp;
    }

    public void setAverageTemp(Float averageTemp) {
        this.averageTemp = averageTemp;
    }
}

