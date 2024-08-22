package io.amplicode.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;

@FieldNameConstants
@Getter
@Setter
public class BaseEntity {

    @Id
    private Long id;

    protected static class Fields {}
}