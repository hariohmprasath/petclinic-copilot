package org.springframework.samples.petclinic.api.customers.model;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnerRepository extends JpaRepository<Owner, Integer> { }
