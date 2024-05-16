package com.prueba.tecnica.cantera.prueba.dao;

import com.prueba.tecnica.cantera.prueba.model.Employee;
import com.prueba.tecnica.cantera.prueba.model.Jobs;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao extends CrudRepository<Employee, Long> {
    Optional<Employee> findByNameAndLastName(String name, String lastName);

    Optional<List<Employee>> findByJobs(Jobs job);
}
