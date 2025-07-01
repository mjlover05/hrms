package com.hrms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Payroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "employee_id", unique = true , nullable = false)
    private Employee employee;

    private double basicSalary;
    private double hra;
    private double da;
    private double tax;
    private double pf;
    private double insurance;

    private double otherDeductions;
    private double grossSalary;
    private double netSalary;


    private LocalDate generatedDate = LocalDate.now();


}
