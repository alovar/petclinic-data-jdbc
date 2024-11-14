package io.amplicode.model;

import org.springframework.data.relational.core.mapping.Column;

public class EmbeddedPassportSerial {

    @Column("region")
    private int regionCode;

    @Column("year")
    private int formYear;

    public int getRegionCode() {
        return regionCode;
    }

    public int getFormYear() {
        return formYear;
    }

    public void setRegionCode(int regionCode) {
        this.regionCode = regionCode;
    }

    public void setFormYear(int formYear) {
        this.formYear = formYear;
    }
}