package io.amplicode.repository;

import io.amplicode.model.Vet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

public interface VetRepository extends ListCrudRepository<Vet, Long> {
    Page<Vet> findAll(Pageable pageable);
}