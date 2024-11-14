package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@Setter
@Table(name = "vets")
public class Vet extends Person {
    @MappedCollection(idColumn = "vet_id", keyColumn = "vet_id")
    private Set<VetToSpecialty> vetsToSpecialties;
}