package org.springframework.samples.petclinic.api.visits.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.api.visits.model.Visit;
import org.springframework.samples.petclinic.api.visits.model.VisitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@Timed("petclinic.visit")
@CrossOrigin
@RequestMapping("/visits")
class VisitResource {

    private final VisitRepository visitRepository;

    @PostMapping("/owners/*/pets/{petId}/visits")
    @ResponseStatus(HttpStatus.CREATED)
    Visit create(@RequestBody Visit visit, @PathVariable("petId") int petId) {
        visit.setPetId(petId);
        log.info("Saving visit {}", visit);
        return visitRepository.save(visit);
    }

    @GetMapping("/owners/*/pets/{petId}/visits")
    List<Visit> visits(@PathVariable("petId") int petId) {
        return visitRepository.findByPetId(petId);
    }

    @GetMapping("/pets/visits")
    Visits visitsMultiGet(@RequestParam("petId") List<Integer> petIds) {
        return new Visits(visitRepository.findByPetIdIn(petIds));
    }

    @Value
    static class Visits {
        List<Visit> items;
    }
}
