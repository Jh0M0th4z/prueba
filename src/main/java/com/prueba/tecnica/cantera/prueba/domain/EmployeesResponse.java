package com.prueba.tecnica.cantera.prueba.domain;

import com.prueba.tecnica.cantera.prueba.model.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class EmployeesResponse {

    private List<Employee> employees;
    private boolean success;
}
