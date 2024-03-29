package com.example.HamburgerAdminPanel.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "Interceptor")
public class Interceptor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "interceptionId")
    private long interceptionId;
    @Column(name = "url")
    private String url;
    @Column(name = "apiName")
    private String apiName;
    @Column(name = "executionTime")
    private long executionTime;
    @Temporal(TemporalType.DATE)
    private Date date;

}
