package com.prueba.tecnica.cantera.prueba.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeeTotalHoursResponse {

    @JsonProperty(value = "total_worked_hours")
    private Integer totalWorkedHours;
    private boolean success;
}
