package io.amplicode.model;

import org.springframework.data.relational.core.mapping.Embedded;

public class EmbeddedPassport {

    @Embedded.Empty(prefix = "serial_")
    private EmbeddedPassportSerial passportSerial;

    private String number;

    public EmbeddedPassportSerial getPassportSerial() {
        return passportSerial;
    }

    public String getNumber() {
        return number;
    }

    public void setPassportSerial(EmbeddedPassportSerial passportSerial) {
        this.passportSerial = passportSerial;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}