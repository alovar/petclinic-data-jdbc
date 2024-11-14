package io.amplicode.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.amplicode.model.Vet;
import io.amplicode.rautils.patch.ObjectPatcher;
import io.amplicode.repository.VetRepository;
import jakarta.validation.Valid;
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
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetResource {

    private final VetRepository vetRepository;
    private final ObjectPatcher objectPatcher;

    @GetMapping
    public Page<Vet> getAll(@ParameterObject Pageable pageable) {
        return vetRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public Vet getOne(@PathVariable Long id) {
        Optional<Vet> vetOptional = vetRepository.findById(id);
        return vetOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<Vet> getMany(@RequestParam List<Long> ids) {
        return vetRepository.findAllById(ids);
    }

    @PostMapping
    public Vet create(@RequestBody @Valid Vet vet) {
        return vetRepository.save(vet);
    }

    @PatchMapping("/{id}")
    public Vet patch(@PathVariable Long id, @RequestBody JsonNode patchNode) {
        Vet vet = vetRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        vet = objectPatcher.patchAndValidate(vet, patchNode);

        return vetRepository.save(vet);
    }

    @PatchMapping
    public List<Vet> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) {
        List<Vet> vets = new ArrayList<>(vetRepository.findAllById(ids));

        vets.replaceAll(vet -> objectPatcher.patchAndValidate(vet, patchNode));

        return vetRepository.saveAll(vets);
    }

    @DeleteMapping("/{id}")
    public Vet delete(@PathVariable Long id) {
        Vet vet = vetRepository.findById(id).orElse(null);
        if (vet != null) {
            vetRepository.delete(vet);
        }
        return vet;
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        vetRepository.deleteAllById(ids);
    }
}
