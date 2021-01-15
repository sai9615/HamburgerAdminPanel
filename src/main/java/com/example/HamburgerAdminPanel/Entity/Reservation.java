package com.example.HamburgerAdminPanel.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "reservations")
@Data
public class Reservation {
    @Id
    private String reservationId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date day;
    private String firstName;
    private String lastName;
}
