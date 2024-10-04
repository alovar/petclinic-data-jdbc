package io.amplicode.repository;

import io.amplicode.model.Pet;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;
import java.util.List;

public interface PetRepository extends ListCrudRepository<Pet, Long> {

    <T> List<T> findByIdIn(Collection<Long> ids, Class<T> projection);
}