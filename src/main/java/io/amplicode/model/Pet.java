package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@FieldNameConstants(innerTypeName = "PetFields")
@Getter
@Setter
@Table(name = "pets")
public class Pet extends NamedEntity {

    @Column("birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @MappedCollection(idColumn = "id")
    private Set<Visit> visits = new LinkedHashSet<>();

    AggregateReference<PetType, Long> typeId;

    public static class PetFields extends Fields {}
}