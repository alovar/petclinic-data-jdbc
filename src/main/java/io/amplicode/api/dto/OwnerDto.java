package io.amplicode.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link io.amplicode.model.Owner}
 */
@Value
public class OwnerDto {
    Long id;
    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String address;
    @NotBlank
    String city;
    @Digits(integer = 10, fraction = 0)
    @NotBlank
    String telephone;
    List<PetDto> pets;
}