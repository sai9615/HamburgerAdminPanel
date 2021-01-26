package com.example.HamburgerAdminPanel.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "reservations")
@Data
public class Reservation {
    @Id
    private String reservationId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy HH:mm", timezone = "EST")
    private Date day;
    private String firstName;
    private String lastName;
}
