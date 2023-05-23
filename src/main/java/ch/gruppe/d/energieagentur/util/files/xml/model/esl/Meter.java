package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
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

    public Meter() {
    }

    public Meter(String factoryNo, String internalNo, List<TimePeriod> timePeriodList) {
        this.factoryNo = factoryNo;
        this.internalNo = internalNo;
        this.timePeriodList = timePeriodList;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public Meter setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
        return this;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public Meter setInternalNo(String internalNo) {
        this.internalNo = internalNo;
        return this;
    }

    public List<TimePeriod> getTimePeriodList() {
        return timePeriodList;
    }

    public Meter setTimePeriodList(List<TimePeriod> timePeriodList) {
        this.timePeriodList = timePeriodList;
        return this;
    }
}
