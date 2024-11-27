package io.amplicode.api.dto;

import lombok.Value;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link io.amplicode.model.Pet}
 */
@Value
public class PetDto {
    Long id;
    String name;
    LocalDate birthDate;
    Set<VisitDto> visits;
    PetDeepInfo.PetTypeInfo typeId;
}