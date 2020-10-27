package com.druppel.api.service;

import com.druppel.api.model.Measurement;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class JsonParser {

    private final JSONObject json = new JSONObject();

    /**
     * Parse data into usable json object
     *
     * @param arrayList Measurements arraylist
     */
    public JsonParser(ArrayList<Measurement> arrayList) {
        try {
            // Data
            JSONObject data = new JSONObject();
            AtomicInteger count = new AtomicInteger();
            arrayList.forEach((reading) -> {
                try {
                    JSONObject measurement = new JSONObject();
                    measurement.put("date", reading.getDate());
                    measurement.put("type", reading.getType());
                    measurement.put("value", reading.getValue());
                    data.put(String.valueOf(count), measurement);
                    count.getAndIncrement();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            this.json.put("result", data);
            this.json.put("message", "OK");
            this.json.put("status", "200");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getString() {
        return this.json.toString();
    }

}
