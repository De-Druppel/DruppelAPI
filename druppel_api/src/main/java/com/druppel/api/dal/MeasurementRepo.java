package com.druppel.api.dal;

import com.druppel.api.model.Measurement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface MeasurementRepo extends CrudRepository <Measurement, Integer> {

    /**
    * Returns average of measured values for specific type on specific date for specific microcontroller
     @param  type String for type of the measured value
     @param  date Date for date where the values were measured
     @param  espId int for microcontroller Id
     @return  Float average of measured values
     */
    @Query("SELECT AVG (value) FROM Measurement WHERE type=?1 AND date=?2 AND espId =?3" )
    public Float averageValue (String type, Date date,int espId);


}
