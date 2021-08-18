package org.springframework.samples.petclinic.api.customers.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
class PetRequest {
    private int id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String name;

    private int typeId;
}
