package ch.gruppe.d.energieagentur.util.files.export.model;

import ch.gruppe.d.energieagentur.util.Converter;
import ch.gruppe.d.energieagentur.Config;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

/**
 * This class is used as the CSV Export Model class
 */
public class CSVExport {
    private long timestamp;
    private String value;

    /**
     * Full parameter constructor
     * @param timestamp for easier creation in LocalDateTime
     * @param value for easier creation in BigDecimal
     */
    public CSVExport(LocalDateTime timestamp, BigDecimal value) {
        this.timestamp = Converter.convertLocalDateTimeToMillis(timestamp);
        this.value = new DecimalFormat(Config.DECIMAL_FORMAT).format(value);
    }

    /**
     * gets the value of timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * sets the value of timestamp
     *
     * @return csv
     */
    public CSVExport setTimestamp(LocalDateTime timestamp) {
        this.timestamp = Converter.convertLocalDateTimeToMillis(timestamp);
        return this;
    }

    /**
     * gets the value of value
     */
    public BigDecimal getValue() {
        return new BigDecimal(value);
    }

    /**
     * sets the value of value
     *
     * @return csv
     */
    public CSVExport setValue(BigDecimal value) {
        this.value = new DecimalFormat(Config.DECIMAL_FORMAT).format(value);
        return this;
    }
}
