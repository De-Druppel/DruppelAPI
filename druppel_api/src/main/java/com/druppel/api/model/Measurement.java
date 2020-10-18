package com.druppel.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "measurement" )

public class Measurement {
    @Id
    private Integer id;

    @Column(name = "date")
    private Date date;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Float value;
}
