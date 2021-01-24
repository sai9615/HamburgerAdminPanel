package com.example.HamburgerAdminPanel.Entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "menu")
@Data
public class Menu {
@Id
private String itemId;
private String itemName;
private String category;
private Boolean status;
private String price;
}
