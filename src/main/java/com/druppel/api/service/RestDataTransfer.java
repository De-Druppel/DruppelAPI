package com.druppel.api.service;

import com.druppel.api.model.Measurement;

import java.util.ArrayList;
import java.util.Date;

public class RestDataTransfer {

    public RestDataTransfer() {

    }

    public String get(String espId, String timeframe) {

        // Get data tmp
        Measurement measurement = new Measurement();
        Measurement measurement2 = new Measurement();

        // tmp measurements
        measurement.setDate(new Date());
        measurement.setType("Temperature");
        measurement.setValue((float) 12.23);

        measurement2.setDate(new Date());
        measurement2.setType("Humidity");
        measurement2.setValue((float) 12.23);

        ArrayList<Measurement> arraylist = new ArrayList<>();
        arraylist.add(measurement);
        arraylist.add(measurement2);

        // Pare data
        JsonParser parser = new JsonParser(arraylist);

        return parser.getString();
    }
}
