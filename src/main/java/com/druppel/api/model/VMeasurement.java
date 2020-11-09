package com.druppel.api.model;

import javax.persistence.*;

@Entity
@Table(name = "v_measurements")
public class VMeasurement {

    // Should have an Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "esp_id")
    private int espId;

    @Column(name = "date_created")
    private Date date;

    @Column(name = "date_created")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Float value;

}
