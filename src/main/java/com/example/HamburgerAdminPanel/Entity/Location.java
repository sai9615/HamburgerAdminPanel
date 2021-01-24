package com.example.HamburgerAdminPanel.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
@Data
public class Location {
    @Id
    private String  locationId;
    private String longitude;
    private String latitude;
    private String address;
    private String phone;
    private Boolean status;
}
