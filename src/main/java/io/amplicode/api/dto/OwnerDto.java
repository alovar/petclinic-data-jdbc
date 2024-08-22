package io.amplicode.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.amplicode.model.Pet;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link io.amplicode.model.Owner}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OwnerDto implements Serializable {
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