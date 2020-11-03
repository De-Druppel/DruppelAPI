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
     *
     * @param days the amount of days you want
     * @param espId the esp you want to query on
     * @return List of date,type, average value
     */
    @Query("SELECT date_created, type, value FROM v_measurement WHERE date_created > current_date - interval ?1 day AND esp_id = ?2" )
    public List<MeasurementSummary> getAverageSummary(int days, int espId);

}