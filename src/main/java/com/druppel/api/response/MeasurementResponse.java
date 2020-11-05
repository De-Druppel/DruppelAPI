package com.druppel.api.response;

import java.util.List;

public class MeasurementResponse {
    private int code;
    private String message;
    private String temperatureUnit;
    private List<MeasurementInfo> data;

    public MeasurementResponse(int code, String message, String temperatureUnit) {
        this.code = code;
        this.message = message;
        this.temperatureUnit = temperatureUnit;
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

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public List<MeasurementInfo> getData() {
        return data;
    }

    public void setData(List<MeasurementInfo> data) {
        this.data = data;
    }
}
