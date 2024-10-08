package io.amplicode.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.time.LocalDate;

/**
 * DTO for {@link io.amplicode.model.Visit}
 */
@Value
public class VisitDto {
    Long id;
    LocalDate date;
    @NotBlank
    String description;
}