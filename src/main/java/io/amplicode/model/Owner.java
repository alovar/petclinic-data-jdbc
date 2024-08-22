package io.amplicode.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@FieldNameConstants
@Table(name = "owners")
public class Owner extends Person {

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @MappedCollection(idColumn = "id", keyColumn = "owner_id")
    private List<Pet> pets = new ArrayList<>();

    public static class Fields extends NamedEntity.Fields {
    }
}