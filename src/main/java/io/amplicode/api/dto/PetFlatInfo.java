package io.amplicode.api.dto;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

/**
 * Projection for {@link io.amplicode.model.Pet}
 */
public interface PetFlatInfo {
    Long getId();

    String getName();

    LocalDate getBirthDate();

    @Value("#{target.type.id}")
    Long getTypeId();

    @Value("#{target.owner.id}")
    Long getOwnerId();
}