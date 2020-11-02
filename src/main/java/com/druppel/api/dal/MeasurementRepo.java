package com.druppel.api.dal;

import com.druppel.api.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementRepo extends JpaRepository <Measurement, Integer> {

    /**
    * Returns average of measured values for specific type on specific date for specific microcontroller
     @param  type String for type of the measured value
     @param  date Date for date where the values were measured
     @param  espId int for microcontroller Id
     @return  Float average of measured values
     */
    @Query("SELECT AVG (value) FROM Measurement WHERE type=?1 AND date=?2 AND espId =?3" )
    public Float averageValue (String type, Date date,int espId);

    /**
     * Returns list of average of measured values grouped by day and type on a specific time interval
        * using interface-based Data JPA Projections
     @param  date1 Date for date where the values were measured
     @param  date2 Date for date where the values were measured
     @return  List of date,type, average value
     */

    @Query("Select date As date, type As type, avg (value) AS value From Measurement Where date >= ?1 and date<=?2 and espId =?3 group by type,date" )
    public List<MeasurementSummary> getAverageSummary(Date date1, Date date2,int espId);


}