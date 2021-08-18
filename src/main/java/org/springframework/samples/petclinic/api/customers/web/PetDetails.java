package org.springframework.samples.petclinic.api.customers.web;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.api.customers.model.Pet;
import org.springframework.samples.petclinic.api.customers.model.PetType;

import java.util.Date;


@Data
class PetDetails {

    private long id;

    private String name;

    private String owner;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private PetType type;

    PetDetails(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.owner = pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName();
        this.birthDate = pet.getBirthDate();
        this.type = pet.getType();
    }
}
