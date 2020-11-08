package com.druppel.api.response;

import com.druppel.api.dal.MeasurementSummary;

import java.util.List;

public class MeasurementResponse {
    private int code;
    private String message;
    private List<MeasurementSummary> data;

    public MeasurementResponse() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MeasurementSummary> getData() {
        return data;
    }

    public void setData(List<MeasurementSummary> data) {
        this.data = data;
    }
}
