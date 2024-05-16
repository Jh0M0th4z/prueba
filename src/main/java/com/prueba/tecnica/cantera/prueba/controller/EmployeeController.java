package com.prueba.tecnica.cantera.prueba.controller;

import com.prueba.tecnica.cantera.prueba.domain.*;
import com.prueba.tecnica.cantera.prueba.model.Employee;
import com.prueba.tecnica.cantera.prueba.services.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    @Autowired
    private Employees employees;

    @PostMapping(
            value = "/add",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<AddEmployeeResponse> addEmployee(@RequestBody AddEmployeeRequest request){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(
                employees.addEmployee(request),
                httpHeaders,
                HttpStatus.CREATED);
    }

    @GetMapping(
            value = "/getJob/{jobId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<EmployeesResponse> getEmployees(@PathVariable Long jobId){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(
                employees.getEmployees(jobId),
                httpHeaders,
                HttpStatus.OK);
    }

    @GetMapping(
            value = "/getTotalHours/{employeeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<EmployeeTotalHoursResponse> getTotalHours(
            @PathVariable Long employeeId,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(
                employees.getTotalHours(employeeId, startDate, endDate),
                httpHeaders,
                HttpStatus.OK);
    }

    @GetMapping(
            value = "/getPayment/{employeeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    public ResponseEntity<EmployeePaymentResponse> getPayment(
            @PathVariable Long employeeId,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate){
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(
                employees.getPayment(employeeId, startDate, endDate),
                httpHeaders,
                HttpStatus.OK);
    }

}
