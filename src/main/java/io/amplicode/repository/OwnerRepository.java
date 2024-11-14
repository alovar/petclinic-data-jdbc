package io.amplicode.repository;

import io.amplicode.model.Owner;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

//@Validated
public interface OwnerRepository extends ListCrudRepository<Owner, Long>, CustomOwnerRepository {

}