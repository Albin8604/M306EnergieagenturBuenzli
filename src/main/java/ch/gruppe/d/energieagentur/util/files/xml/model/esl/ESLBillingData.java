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
public class ESLBillingData {
    private Header header;
    private Meter meter;

    public ESLBillingData() {
    }

    public ESLBillingData(Header header, Meter meter) {
        this.header = header;
        this.meter = meter;
    }

    public Header getHeader() {
        return header;
    }

    public ESLBillingData setHeader(Header header) {
        this.header = header;
        return this;
    }

    public Meter getMeter() {
        return meter;
    }

    public ESLBillingData setMeter(Meter meter) {
        this.meter = meter;
        return this;
    }
}
