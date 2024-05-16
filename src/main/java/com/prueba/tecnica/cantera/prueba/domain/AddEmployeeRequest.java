package com.prueba.tecnica.cantera.prueba.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AddEmployeeRequest {

    @JsonProperty(value = "gender_id")
    private Long genderId;
    @JsonProperty(value = "job_id")
    private Long jobId;
    private String name;
    @JsonProperty(value = "last_name")
    private String lastName;
    private String birthDate;

}
