package com.hrms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private int age;
    private String designation;
    private String previousCompany;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private User user; // User for login

    // Getters and Setters
}

