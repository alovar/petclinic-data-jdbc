package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "vet_specialties")
public class VetToSpecialty {
    private AggregateReference<Vet, Long> vetId;
    private AggregateReference<Specialty, Long> specialtyId;
}