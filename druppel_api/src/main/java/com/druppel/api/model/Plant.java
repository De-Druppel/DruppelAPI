package com.druppel.api.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plant")
public class Plant {
    @Id
    private Integer idEsp;

    @OneToMany(targetEntity = Measurement.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "esp_id_plant", referencedColumnName = "esp_id")
    private List<Measurement> measurementList;

    public Plant() {
    }

    public Integer getIdEsp() {
        return idEsp;
    }

    public void setIdEsp(Integer idEsp) {
        this.idEsp = idEsp;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }
}