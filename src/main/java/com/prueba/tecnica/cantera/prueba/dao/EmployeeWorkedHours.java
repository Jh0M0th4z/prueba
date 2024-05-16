package com.prueba.tecnica.cantera.prueba.dao;

import com.prueba.tecnica.cantera.prueba.model.EmployeWorkedHours;
import com.prueba.tecnica.cantera.prueba.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeWorkedHours extends JpaRepository<EmployeWorkedHours, Long> {

    Optional<List<EmployeWorkedHours>> findByEmployeeAndWorkedDateBetween(Employee employee, Date workedDateStart, Date workedDateEnd);
}
