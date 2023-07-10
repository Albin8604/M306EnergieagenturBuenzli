package ch.gruppe.d.energieagentur.util.files.export.model;

import ch.gruppe.d.energieagentur.util.Converter;

import java.time.LocalDateTime;

/**
 * This class is used as a Model class for the json export
 */
public class Zaehlerstand {
    private String ts;
    private double value;

    public Zaehlerstand(String ts, double value) {
        this.ts = ts;
        this.value = value;
    }

    public Zaehlerstand(LocalDateTime ts, double value) {
        this.ts = String.valueOf(Converter.convertLocalDateTimeToMillis(ts));
        this.value = value;
    }

    public String getTs() {
        return ts;
    }

    public Zaehlerstand setTs(LocalDateTime ts) {
        this.ts = String.valueOf(Converter.convertLocalDateTimeToMillis(ts));
        return this;
    }

    public double getValue() {
        return value;
    }

    public Zaehlerstand setValue(double value) {
        this.value = value;
        return this;
    }
}
