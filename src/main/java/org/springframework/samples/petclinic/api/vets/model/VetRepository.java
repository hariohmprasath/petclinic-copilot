package org.springframework.samples.petclinic.api.vets.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet, Integer> {
}
