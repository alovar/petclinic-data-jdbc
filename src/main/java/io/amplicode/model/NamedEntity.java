package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.relational.core.mapping.Column;

@FieldNameConstants
@Getter
@Setter
public class NamedEntity extends BaseEntity {

    @Column("name")
    private String name;

    protected static class Fields extends BaseEntity.Fields {}
}