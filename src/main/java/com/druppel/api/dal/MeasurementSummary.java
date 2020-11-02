package com.druppel.api.dal;

import java.util.Date;

public interface MeasurementSummary {
    Date getDate();
    String getType();
    Float getValue();

}
