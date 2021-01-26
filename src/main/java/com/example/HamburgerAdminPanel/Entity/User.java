package com.example.HamburgerAdminPanel.Entity;

import lombok.Data;



import javax.persistence.*;


@Entity
@Data
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private boolean isActive;

    @Column(name = "roles")
    private String roles;
}
