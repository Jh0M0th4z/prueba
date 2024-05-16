package com.prueba.tecnica.cantera.prueba.services;

import com.prueba.tecnica.cantera.prueba.domain.*;
import com.prueba.tecnica.cantera.prueba.model.Employee;

import java.util.List;

public interface Employees {

    AddEmployeeResponse addEmployee(AddEmployeeRequest request);

    EmployeesResponse getEmployees(Long jobId);

    EmployeeTotalHoursResponse getTotalHours(Long employeeId, String startDate, String endDate);

    EmployeePaymentResponse getPayment(Long employeeId, String startDate, String endDate);

}
