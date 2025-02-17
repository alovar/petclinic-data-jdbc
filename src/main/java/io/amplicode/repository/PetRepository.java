package io.amplicode.repository;

import io.amplicode.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

public interface PetRepository extends ListCrudRepository<Pet, Long> {
    Page<Pet> findAll(Pageable pageable);
}