package com.druppel.api.dal;

import java.util.Date;

public class MeasurementSummaryImpl implements MeasurementSummary {

    Date date;
    String type;
    Float value;

    public MeasurementSummaryImpl() {
    }

    public MeasurementSummaryImpl(Date date, String type, Float value) {
        this.date = date;
        this.type = type;
        this.value = value;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Float getValue() {
        return value;
    }

}
