package com.druppel.api.dal;

import com.druppel.api.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MeasurementRepo extends JpaRepository <Measurement, Integer> {
}