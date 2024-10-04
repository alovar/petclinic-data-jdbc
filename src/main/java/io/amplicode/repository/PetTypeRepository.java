package io.amplicode.repository;

import io.amplicode.model.PetType;
import org.springframework.data.repository.ListCrudRepository;

public interface PetTypeRepository extends ListCrudRepository<PetType, Long> {
}