package io.amplicode.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.amplicode.api.dto.OwnerDto;
import io.amplicode.api.dto.OwnerFilter;
import io.amplicode.api.mapper.OwnerMapper;
import io.amplicode.model.Owner;
import io.amplicode.repository.OwnerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerResource {

    private final OwnerRepository ownerRepository;
    private final ObjectMapper objectMapper;
    private final OwnerMapper ownerMapper;

    @GetMapping
    public List<OwnerDto> getList(@ModelAttribute OwnerFilter filter) {
        List<Owner> owners = ownerRepository.findAll(filter);
        return owners.stream()
                .map(ownerMapper::toDto)
                .toList();
    }

    @GetMapping("/pageable")
    public Page<OwnerDto> getListWithPagination(@ModelAttribute OwnerFilter filter, Pageable pageable) {
        Page<Owner> owners = ownerRepository.findAll(filter, pageable);
        return owners.map(ownerMapper::toDto);
    }

    @GetMapping("/{id}")
    public OwnerDto getOne(@PathVariable Long id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        return ownerMapper.toDto(ownerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    @GetMapping("/by-ids")
    public List<OwnerDto> getMany(@RequestParam List<Long> ids) {
        List<Owner> owners = ownerRepository.findAllById(ids);
        return owners.stream()
                .map(ownerMapper::toDto)
                .toList();
    }

    @PostMapping
    public OwnerDto create(@RequestBody @Valid OwnerDto dto) {
        Owner owner = ownerMapper.toEntity(dto);
        Owner resultOwner = ownerRepository.save(owner);
        return ownerMapper.toDto(resultOwner);
    }

    @PatchMapping("/{id}")
    public OwnerDto patch(@PathVariable Long id, @RequestBody JsonNode patchNode) throws IOException {
        Owner owner = ownerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        OwnerDto ownerDto = ownerMapper.toDto(owner);
        objectMapper.readerForUpdating(ownerDto).readValue(patchNode);
        ownerMapper.updateWithNull(ownerDto, owner);

        Owner resultOwner = ownerRepository.save(owner);
        return ownerMapper.toDto(resultOwner);
    }

    @PatchMapping
    public List<Long> patchMany(@RequestParam @Valid List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<Owner> owners = ownerRepository.findAllById(ids);

        for (Owner owner : owners) {
            OwnerDto ownerDto = ownerMapper.toDto(owner);
            objectMapper.readerForUpdating(ownerDto).readValue(patchNode);
            ownerMapper.updateWithNull(ownerDto, owner);
        }

        List<Owner> resultOwners = ownerRepository.saveAll(owners);
        return resultOwners.stream()
                .map(Owner::getId)
                .toList();
    }

    @DeleteMapping("/{id}")
    public OwnerDto delete(@PathVariable Long id) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        if (owner != null) {
            ownerRepository.delete(owner);
        }
        return ownerMapper.toDto(owner);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        ownerRepository.deleteAllById(ids);
    }
}