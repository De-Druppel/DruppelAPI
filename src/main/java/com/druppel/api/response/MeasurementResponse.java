package com.druppel.api.response;

import com.druppel.api.dal.MeasurementSummary;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MeasurementResponse {
    private int code;
    private String message;
    private Map<Date,List<Map<String,Object>>>data;

    public MeasurementResponse() {
    }

    public MeasurementResponse(int code, String message) {
        this.code = code;
        this.message = message;
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

    public Map<Date,List<Map<String,Object>>> getData() {
        return data;
    }

    public void setData(Map<Date,List<Map<String,Object>>> data) {
        this.data = data;
    }
}
