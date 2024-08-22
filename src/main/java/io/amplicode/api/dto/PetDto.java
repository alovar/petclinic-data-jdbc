package io.amplicode.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO for {@link io.amplicode.model.Pet}
 */
public record PetDto(Long id, @NotNull String name, LocalDate birthDate, @NotNull Long typeId, @NotNull Long ownerId) {
}
