package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import ch.gruppe.d.energieagentur.util.files.xml.model.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a element model class of the ESL xml files
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TimePeriod {
    @XmlAttribute(name = "end")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime end;

    @XmlElement(name = "ValueRow")
    private List<ValueRow> valueRowList;

    /**
     * Default constructor
     */
    public TimePeriod() {
    }

    /**
     * Constructor
     *
     * @param end          LocalDateTime
     * @param valueRowList List<ValueRow>
     */
    public TimePeriod(LocalDateTime end, List<ValueRow> valueRowList) {
        this.end = end;
        this.valueRowList = valueRowList;
    }

    /**
     * Constructor
     *
     * @return LocalDateTime
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Constructor
     *
     * @param end LocalDateTime
     * @return TimePeriod
     */
    public TimePeriod setEnd(LocalDateTime end) {
        this.end = end;
        return this;
    }

    /**
     * Constructor
     *
     * @return List<ValueRow>
     */
    public List<ValueRow> getValueRowList() {
        return valueRowList;
    }

    /**
     * Constructor
     *
     * @param valueRowList List<ValueRow>
     * @return TimePeriod
     */
    public TimePeriod setValueRowList(List<ValueRow> valueRowList) {
        this.valueRowList = valueRowList;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    @Override
    public String toString() {
        return "TimePeriod{" +
                "end=" + end +
                ", valueRowList=" + Arrays.toString(valueRowList.toArray()) +
                '}';
    }
}
