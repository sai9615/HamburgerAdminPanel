package com.example.HamburgerAdminPanel.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "OpenHours")
@Data
public class OpenHours {
    @Id
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "EST")
    private Date fromTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "EST")
    private Date toTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yy", timezone = "EST")
    private Date date;
    private String dayOfWeek;
}
