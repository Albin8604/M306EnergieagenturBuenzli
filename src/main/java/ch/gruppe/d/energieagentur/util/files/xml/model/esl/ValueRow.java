package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import ch.gruppe.d.energieagentur.util.files.xml.model.adapter.BigDecimalAdapter;
import ch.gruppe.d.energieagentur.util.files.xml.model.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
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

    public ValueRow() {
    }

    public ValueRow(String obis, LocalDateTime valueTimeStamp, BigDecimal value, String status) {
        this.obis = obis;
        this.valueTimeStamp = valueTimeStamp;
        this.value = value;
        this.status = status;
    }

    public String getObis() {
        return obis;
    }

    public ValueRow setObis(String obis) {
        this.obis = obis;
        return this;
    }

    public LocalDateTime getValueTimeStamp() {
        return valueTimeStamp;
    }

    public ValueRow setValueTimeStamp(LocalDateTime valueTimeStamp) {
        this.valueTimeStamp = valueTimeStamp;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public ValueRow setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ValueRow setStatus(String status) {
        this.status = status;
        return this;
    }
}
