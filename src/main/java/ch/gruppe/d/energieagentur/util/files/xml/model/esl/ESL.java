package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is the root model class of the ESL xml files
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ESLBillingData")
public class ESL {
    @XmlElement(name = "Header", type = Header.class)
    private Header header;

    @XmlElement(name = "Meter", type = Meter.class)
    private Meter meter;

    /**
     * Default constructor
     */
    public ESL() {
    }

    /**
     * Constructor
     *
     * @param header Header
     * @param meter  Meter
     */
    public ESL(Header header, Meter meter) {
        this.header = header;
        this.meter = meter;
    }

    /**
     * Constructor
     *
     * @return Header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Constructor
     *
     * @param header Header
     * @return ESL
     */
    public ESL setHeader(Header header) {
        this.header = header;
        return this;
    }

    /**
     * Constructor
     *
     * @return Meter
     */
    public Meter getMeter() {
        return meter;
    }

    /**
     * Constructor
     *
     * @param meter Meter
     * @return ESL
     */
    public ESL setMeter(Meter meter) {
        this.meter = meter;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    @Override
    public String toString() {
        return "ESLBillingData{" +
                "header=" + header +
                ", meter=" + meter +
                '}';
    }
}
