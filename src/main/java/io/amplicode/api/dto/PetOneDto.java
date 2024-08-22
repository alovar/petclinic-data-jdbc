package io.amplicode.api.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link io.amplicode.model.Pet}
 */
public record PetOneDto(Long id, @NotNull String name, @NotNull Long typeId) {
}