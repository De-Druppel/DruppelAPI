package com.druppel.api.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "v_measurement")
public class VMeasurement {

    // Should have an Id
    @Id
    private Integer id;

    @Column(name = "esp_id")
    private int espId;

    @Column(name = "date_created")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Double value;

    public VMeasurement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEspId() {
        return espId;
    }

    public void setEspId(int espId) {
        this.espId = espId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
