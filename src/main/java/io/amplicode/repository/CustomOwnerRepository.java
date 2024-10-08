package io.amplicode.repository;

import io.amplicode.api.dto.OwnerFilter;
import io.amplicode.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomOwnerRepository {
    List<Owner> findAll(OwnerFilter filter);

    Page<Owner> findAll(OwnerFilter filter, Pageable pageable);
}
