package com.druppel.api.dal;

import com.druppel.api.model.VMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VMeasurementRepo extends JpaRepository<VMeasurement, Integer> {

    /**
     * Returns list of average of measured values grouped by day and type on a specific time interval
     * using interface-based Data JPA Projections
     *
     * @param queryDate the amount of days you want
     * @param espId the esp you want to query on
     * @param type the type you want to query on
     * @return List of date,type, average value
     */
    @Query("SELECT date AS date, type AS type, value AS value FROM VMeasurement WHERE date >= ?1 AND espId = ?2 AND type LIKE %?3%")
    public List<MeasurementSummary> getAverageSummary(Date queryDate, int espId, String type);

}
