package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@Setter
@Table(name = "specialties")
public class Specialty extends NamedEntity {
    @MappedCollection(idColumn = "specialty_id", keyColumn = "specialty_id")
    private Set<VetToSpecialty> vetsToSpecialties;

}