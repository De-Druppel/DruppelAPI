package com.druppel.api.service;

import org.json.JSONException;
import org.json.JSONObject;

public class RestDataTransfer {
    public RestDataTransfer() {
    }

    public String get(String espId, String timeframe) {

        // JSON OBJECT EXAMPLE
        JSONObject result = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            data.put("Temprature 1", "23");
            data.put("Temprature 2", "54");
            data.put("Temprature 3", "12");

            result.put("Data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
