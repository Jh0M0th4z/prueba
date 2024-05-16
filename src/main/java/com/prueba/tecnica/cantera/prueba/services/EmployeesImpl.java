package com.prueba.tecnica.cantera.prueba.services;

import com.prueba.tecnica.cantera.prueba.dao.EmployeeDao;
import com.prueba.tecnica.cantera.prueba.dao.EmployeeWorkedHours;
import com.prueba.tecnica.cantera.prueba.domain.*;
import com.prueba.tecnica.cantera.prueba.model.EmployeWorkedHours;
import com.prueba.tecnica.cantera.prueba.model.Employee;
import com.prueba.tecnica.cantera.prueba.model.Gender;
import com.prueba.tecnica.cantera.prueba.model.Jobs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeesImpl implements Employees {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeeWorkedHours employeeWorkedHours;

    @Override
    public AddEmployeeResponse addEmployee(AddEmployeeRequest request) {
        try {
            if (validationsAddEmployee(request)) {

                Gender gender = new Gender();
                gender.setId(request.getGenderId());

                Jobs jobs = new Jobs();
                jobs.setId(request.getJobId());

                Employee newEmployee = new Employee();
                newEmployee.setGender(gender);
                newEmployee.setJobs(jobs);
                newEmployee.setName(request.getName().trim());
                newEmployee.setLastName(request.getLastName().trim());
                newEmployee.setBirthdate(Date.valueOf(request.getBirthDate()));

                employeeDao.save(newEmployee);

                return AddEmployeeResponse.builder()
                        .id(Integer.valueOf(String.valueOf(newEmployee.getId())))
                        .success(true)
                        .build();
            }
        } catch (Exception e) {
            log.warn("Ocurrio un error al guardar al empleado", e.getCause());
        }

        return AddEmployeeResponse.builder()
                .id(0)
                .success(false)
                .build();
    }

    @Override
    public EmployeesResponse getEmployees(Long jobId) {

        Jobs job = new Jobs();
        job.setId(jobId);
        Optional<List<Employee>> employees = employeeDao.findByJobs(job);

        if (!employees.isPresent() || employees.get().isEmpty()) {
            return EmployeesResponse.builder()
                    .success(false)
                    .build();
        }

        return EmployeesResponse.builder()
                .employees(employees.get()
                        .stream()
                        .sorted(Comparator.comparing(Employee::getLastName))
                        .collect(Collectors.toList()))
                .success(true)
                .build();
    }

    @Override
    public EmployeeTotalHoursResponse getTotalHours(Long employeeId, String startDate, String endDate) {
        Optional<Employee> employee = employeeDao.findById(employeeId);

        if (!employee.isPresent()) {
            return EmployeeTotalHoursResponse.builder()
                    .success(false)
                    .build();
        }

        if (!validateDate(startDate, endDate)) {
            return EmployeeTotalHoursResponse.builder()
                    .success(false)
                    .build();
        }

        Optional<List<EmployeWorkedHours>>  listEmployeeWorkedHours = employeeWorkedHours.findByEmployeeAndWorkedDateBetween(
                employee.get(), Date.valueOf(startDate), Date.valueOf(endDate));

        if (!listEmployeeWorkedHours.isPresent() || listEmployeeWorkedHours.get().isEmpty()) {
            return EmployeeTotalHoursResponse.builder()
                    .success(false)
                    .build();
        }

        return EmployeeTotalHoursResponse.builder()
                .totalWorkedHours(listEmployeeWorkedHours.get().stream()
                        .map(EmployeWorkedHours::getWorkedHours)
                        .reduce(0, Integer::sum))
                .success(true)
                .build();
    }

    @Override
    public EmployeePaymentResponse getPayment(Long employeeId, String startDate, String endDate) {
        Optional<Employee> employee = employeeDao.findById(employeeId);

        if (!employee.isPresent()) {
            return EmployeePaymentResponse.builder()
                    .success(false)
                    .build();
        }

        if (!validateDate(startDate, endDate)) {
            return EmployeePaymentResponse.builder()
                    .success(false)
                    .build();
        }

        Optional<List<EmployeWorkedHours>>  listEmployeeWorkedHours = employeeWorkedHours.findByEmployeeAndWorkedDateBetween(
                employee.get(), Date.valueOf(startDate), Date.valueOf(endDate));

        if (!listEmployeeWorkedHours.isPresent() || listEmployeeWorkedHours.get().isEmpty()) {
            return EmployeePaymentResponse.builder()
                    .success(false)
                    .build();
        }

        Integer totalHours = listEmployeeWorkedHours.get().stream()
                .map(EmployeWorkedHours::getWorkedHours)
                .reduce(0, Integer::sum);

        // suponiendo que el salario es por mes
        return EmployeePaymentResponse.builder()
                .payment(
                        ((employee.get().getJobs().getSalary()
                                .divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP)) // se divide entre 30 dias
                                .divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP)) // y despues entre 8 hrs jornada laboral
                                .multiply(BigDecimal.valueOf(totalHours.intValue())))
                .success(true)
                .build();

    }

    private boolean validationsAddEmployee(AddEmployeeRequest request) {

        Optional<Employee> employee = employeeDao.findByNameAndLastName(
                request.getName().trim(),
                request.getLastName().trim());

        if (!employee.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate ldBirthDate = LocalDate.parse(request.getBirthDate(), formatter);
            Period age = Period.between(ldBirthDate, LocalDate.now());
            return age.getYears() > 18;
        }
        return false;

    }

    private boolean validateDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try {
            java.util.Date from = sdf.parse(startDate);
            java.util.Date to = sdf.parse(endDate);

            return from.before(to);

        } catch (ParseException pe) {
            log.warn("Error al validar las fechas", pe);
        }

        return false;

    }
}
