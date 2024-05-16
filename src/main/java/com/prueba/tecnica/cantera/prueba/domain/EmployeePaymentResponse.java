package com.prueba.tecnica.cantera.prueba.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class EmployeePaymentResponse {

    private BigDecimal payment;
    private boolean success;

}
