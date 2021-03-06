package org.springframework.samples.petclinic.api.customers.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.api.customers.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Timed("petclinic.pet")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
class PetResource {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @GetMapping("/owners/petTypes")
    public List<PetType> getPetTypes() {
        return petRepository.findPetTypes();
    }

    @PostMapping("/owners/{ownerId}/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public Pet processCreationForm(@RequestBody PetRequest petRequest,
        @PathVariable("ownerId") int ownerId) throws Exception {
        final Pet pet = new Pet();

        final Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        Map<String, Object> data = new HashMap<>();
        data.put("isPresent", optionalOwner.isPresent());
        data.put("ownerId", ownerId);

        Owner owner = optionalOwner.orElseThrow(() -> (Exception) new ResourceNotFoundException("Owner " + ownerId + " not found"));
        owner.addPet(pet);

        // Save Subsegment
        return save(pet, petRequest);
    }

    @PutMapping("/owners/*/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@RequestBody PetRequest petRequest) {
        int petId = petRequest.getId();
        Pet pet = findPetById(petId);
        save(pet, petRequest);
    }

    private Pet save(final Pet pet, final PetRequest petRequest) {
        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());

        petRepository.findPetTypeById(petRequest.getTypeId())
            .ifPresent(pet::setType);

        log.info("Saving pet {}", pet);
        return petRepository.save(pet);
    }

    @GetMapping("owners/*/pets/{petId}")
    public PetDetails findPet(@PathVariable("petId") int petId) {
        return new PetDetails(findPetById(petId));
    }


    private Pet findPetById(int petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (!pet.isPresent()) {
            throw new ResourceNotFoundException("Pet " + petId + " not found");
        }
        return pet.get();
    }

}
