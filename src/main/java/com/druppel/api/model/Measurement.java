package com.druppel.api.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "measurement")

public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_created")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Float value;

    @JoinColumn(name = "client_id")
    @ManyToOne()
    private Plant plant;

    public Measurement() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Plant getPlant() { return plant; }

    public void setPlant(Plant plant) { this.plant = plant; }
}

