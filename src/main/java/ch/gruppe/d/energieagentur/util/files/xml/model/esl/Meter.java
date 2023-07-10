package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a element model class of the ESL xml files
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Meter {
    @XmlAttribute(name = "factoryNo")
    private String factoryNo;

    @XmlAttribute(name = "internalNo")
    private String internalNo;

    @XmlElement(name = "TimePeriod")
    private List<TimePeriod> timePeriodList;

    /**
     * Default constructor
     */
    public Meter() {
    }

    /**
     * Constructor
     *
     * @param factoryNo     String
     * @param internalNo    String
     * @param timePeriodList List<TimePeriod>
     */
    public Meter(String factoryNo, String internalNo, List<TimePeriod> timePeriodList) {
        this.factoryNo = factoryNo;
        this.internalNo = internalNo;
        this.timePeriodList = timePeriodList;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getFactoryNo() {
        return factoryNo;
    }

    /**
     * Constructor
     *
     * @param factoryNo String
     * @return Meter
     */
    public Meter setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getInternalNo() {
        return internalNo;
    }

    /**
     * Constructor
     *
     * @param internalNo String
     * @return Meter
     */
    public Meter setInternalNo(String internalNo) {
        this.internalNo = internalNo;
        return this;
    }

    /**
     * Constructor
     *
     * @return List<TimePeriod>
     */
    public List<TimePeriod> getTimePeriodList() {
        return timePeriodList;
    }

    /**
     * Constructor
     *
     * @param timePeriodList List<TimePeriod>
     * @return Meter
     */
    public Meter setTimePeriodList(List<TimePeriod> timePeriodList) {
        this.timePeriodList = timePeriodList;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Meter{" +
                "factoryNo='" + factoryNo + '\'' +
                ", internalNo='" + internalNo + '\'' +
                ", timePeriodList=" + Arrays.toString(timePeriodList.toArray()) +
                '}';
    }
}
