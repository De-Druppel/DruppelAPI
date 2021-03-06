package com.druppel.api.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plant")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column( name= "esp_id")
    private int idEsp;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "plant", fetch = FetchType.EAGER)
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

    public void addMeasurement(Measurement measurement) {this.measurementList.add(measurement);}
}