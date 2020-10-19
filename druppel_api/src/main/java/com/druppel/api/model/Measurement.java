package com.druppel.api.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "measurement" )

public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Float value;
}

