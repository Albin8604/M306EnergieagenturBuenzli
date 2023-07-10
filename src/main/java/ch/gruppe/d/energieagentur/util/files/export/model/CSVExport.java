package ch.gruppe.d.energieagentur.util.files.export.model;

import java.math.BigDecimal;

public class CSVExport {
    private long timestamp;
    private BigDecimal value;

    public CSVExport(long timestamp, BigDecimal value) {
        this.timestamp = timestamp;
        this.value = value;
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
    public CSVExport setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * gets the value of value
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * sets the value of value
     *
     * @return csv
     */
    public CSVExport setValue(BigDecimal value) {
        this.value = value;
        return this;
    }
}