package io.amplicode.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.model.Pet;
import io.amplicode.rautils.patch.ObjectPatcher;
import io.amplicode.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetResource {

    private final PetRepository petRepository;

    private final ObjectPatcher objectPatcher;

    @GetMapping
    public Page<Pet> getAll(@ParameterObject Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Pet getOne(@PathVariable Long id) {
        Optional<Pet> petOptional = petRepository.findById(id);
        return petOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Pet> getMany(@RequestParam List<Long> ids) {
        return petRepository.findAllById(ids);
    }

    @PostMapping
    public Pet create(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PatchMapping("/{id}")
    public Pet patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        Pet pet = petRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        pet = objectPatcher.patchAndValidate(pet, patchNode);

        return petRepository.save(pet);
    }

    @PatchMapping
    public List<Pet> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) {
        List<Pet> pets = new ArrayList<>(petRepository.findAllById(ids));

        pets.replaceAll(pet -> objectPatcher.patchAndValidate(pet, patchNode));

        return petRepository.saveAll(pets);
    }

    @DeleteMapping("/{id}")
    public Pet delete(@PathVariable Long id) {
        Pet pet = petRepository.findById(id).orElse(null);
        if (pet != null) {
            petRepository.delete(pet);
        }
        return pet;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        petRepository.deleteAllById(ids);
    }
}
