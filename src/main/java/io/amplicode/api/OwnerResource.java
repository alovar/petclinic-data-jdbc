package io.amplicode.api;

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
                .map(ownerMapper::toOwnerDto)
                .toList();
    }

    @GetMapping("/pageable")
    public Page<OwnerDto> getListWithPagination(@ModelAttribute OwnerFilter filter, Pageable pageable) {
        Page<Owner> owners = ownerRepository.findAll(filter, pageable);
        return owners.map(ownerMapper::toOwnerDto);
    }

    @GetMapping("/{id}")
    public OwnerDto getOne(@PathVariable Long id) {
        Optional<Owner> ownerOptional = ownerRepository.findById(id);
        return ownerMapper.toOwnerDto(ownerOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    @GetMapping("/by-ids")
    public List<OwnerDto> getMany(@RequestParam List<Long> ids) {
        List<Owner> owners = ownerRepository.findAllById(ids);
        return owners.stream()
                .map(ownerMapper::toOwnerDto)
                .toList();
    }

    @PostMapping
    public OwnerDto create(@RequestBody @Valid OwnerDto dto) {
        Owner owner = ownerMapper.toEntity(dto);
        Owner resultOwner = ownerRepository.save(owner);
        return ownerMapper.toOwnerDto(resultOwner);
    }

    @DeleteMapping("/{id}")
    public OwnerDto delete(@PathVariable Long id) {
        Owner owner = ownerRepository.findById(id).orElse(null);
        if (owner != null) {
            ownerRepository.delete(owner);
        }
        return ownerMapper.toOwnerDto(owner);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        ownerRepository.deleteAllById(ids);
    }

    @PutMapping("/{id}")
    public OwnerDto update(@PathVariable Long id, @RequestBody @Valid OwnerDto dto) {
        Owner owner = ownerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
        ownerMapper.updateWithNull(dto, owner);
        Owner resultOwner = ownerRepository.save(owner);
        return ownerMapper.toOwnerDto(resultOwner);
    }
}