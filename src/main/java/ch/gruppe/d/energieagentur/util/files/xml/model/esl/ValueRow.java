package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import ch.gruppe.d.energieagentur.util.files.xml.model.adapter.BigDecimalAdapter;
import ch.gruppe.d.energieagentur.util.files.xml.model.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * This class is a element model class of the ESL xml files
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ValueRow {
    @XmlAttribute(name = "obis")
    private String obis;

    @XmlAttribute(name = "valueTimeStamp")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime valueTimeStamp;

    @XmlAttribute(name = "value")
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal value;

    @XmlAttribute(name = "status")
    private String status;

    /**
     * Default constructor
     */
    public ValueRow() {
    }

    /**
     * Constructor
     *
     * @param obis           String
     * @param valueTimeStamp LocalDateTime
     * @param value          BigDecimal
     * @param status         String
     */
    public ValueRow(String obis, LocalDateTime valueTimeStamp, BigDecimal value, String status) {
        this.obis = obis;
        this.valueTimeStamp = valueTimeStamp;
        this.value = value;
        this.status = status;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getObis() {
        return obis;
    }

    /**
     * Constructor
     *
     * @param obis String
     * @return ValueRow
     */
    public ValueRow setObis(String obis) {
        this.obis = obis;
        return this;
    }

    /**
     * Constructor
     *
     * @return LocalDateTime
     */
    public LocalDateTime getValueTimeStamp() {
        return valueTimeStamp;
    }

    /**
     * Constructor
     *
     * @param valueTimeStamp LocalDateTime
     * @return ValueRow
     */
    public ValueRow setValueTimeStamp(LocalDateTime valueTimeStamp) {
        this.valueTimeStamp = valueTimeStamp;
        return this;
    }

    /**
     * Constructor
     *
     * @return BigDecimal
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Constructor
     *
     * @param value BigDecimal
     * @return ValueRow
     */
    public ValueRow setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getStatus() {
        return status;
    }

    /**
     * Constructor
     *
     * @param status String
     * @return ValueRow
     */
    public ValueRow setStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    @Override
    public String toString() {
        return "ValueRow{" +
                "obis='" + obis + '\'' +
                ", valueTimeStamp=" + valueTimeStamp +
                ", value=" + value +
                ", status='" + status + '\'' +
                '}';
    }
}
