package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table(name = "specialties")
public class Specialty extends NamedEntity {
}