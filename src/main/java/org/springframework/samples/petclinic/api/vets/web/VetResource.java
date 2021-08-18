package org.springframework.samples.petclinic.api.vets.web;

import lombok.RequiredArgsConstructor;
import org.springframework.samples.petclinic.api.vets.model.Vet;
import org.springframework.samples.petclinic.api.vets.model.VetRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/vets")
@RestController
@RequiredArgsConstructor
@CrossOrigin
class VetResource {

    private final VetRepository vetRepository;

    @GetMapping
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }
}
