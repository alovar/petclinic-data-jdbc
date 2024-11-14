package io.amplicode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Embedded;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    private String serial;
    private String number;
//    @Embedded.Empty(prefix = "pref1_")
//    private PassportNumber number;
}
