package com.prueba.tecnica.cantera.prueba.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AddEmployeeResponse {

    private int id;
    private boolean success;

}
