package ch.gruppe.d.energieagentur.util.files.export.model;

import ch.gruppe.d.energieagentur.util.Converter;

import java.time.LocalDateTime;

/**
 * This class is used as a Model class for the json export
 */
public class Zaehlerstand {
    private String ts;
    private double value;

    /**
     * Full args Constructor
     * @param ts timestamp for easier creation in LocalDateTime
     * @param value value
     */
    public Zaehlerstand(LocalDateTime ts, double value) {
        this.ts = String.valueOf(Converter.convertLocalDateTimeToMillis(ts));
        this.value = value;
    }

    /**
     * gets the value of ts
     */
    public String getTs() {
        return ts;
    }

    /**
     * sets the value of ts
     *
     * @return Zaehlerstand
     */
    public Zaehlerstand setTs(String ts) {
        this.ts = ts;
        return this;
    }

    /**
     * gets the value of value
     */
    public double getValue() {
        return value;
    }

    /**
     * sets the value of value
     *
     * @return Zaehlerstand
     */
    public Zaehlerstand setValue(double value) {
        this.value = value;
        return this;
    }
}
