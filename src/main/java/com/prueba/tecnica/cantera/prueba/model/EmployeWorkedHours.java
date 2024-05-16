package com.prueba.tecnica.cantera.prueba.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Employee_worked_hours")
public class EmployeWorkedHours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @Column(name = "worked_hours")
    private Integer workedHours;

    @Column(name = "worked_date")
    private Date workedDate;

}
