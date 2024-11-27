package io.amplicode.repository;

import io.amplicode.model.Owner;
import org.springframework.data.repository.ListCrudRepository;

public interface OwnerRepository extends ListCrudRepository<Owner, Long>, CustomOwnerRepository {
}