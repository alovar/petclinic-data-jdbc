package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@FieldNameConstants
@Getter
@Setter
public class NamedEntity extends BaseEntity {

    private String name;

    protected static class Fields extends BaseEntity.Fields {}
}