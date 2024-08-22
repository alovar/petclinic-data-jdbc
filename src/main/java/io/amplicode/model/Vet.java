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

//    @OneToMany
//    @JoinTable(name = "vet_specialties", joinColumns = @JoinColumn(name = "vet_id"),
//            inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    @MappedCollection(idColumn = "id")
    private Set<Specialty> specialties;
}