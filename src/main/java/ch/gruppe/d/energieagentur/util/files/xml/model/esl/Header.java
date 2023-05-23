package ch.gruppe.d.energieagentur.util.files.xml.model.esl;

import ch.gruppe.d.energieagentur.util.files.xml.model.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

/**
 * This class is a element model class of the ESL xml files
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Header {
    @XmlAttribute(name = "version")
    private String version;

    @XmlAttribute(name = "created")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime created;

    @XmlAttribute(name = "swSystemNameFrom")
    private String swSystemNameFrom;

    @XmlAttribute(name = "swSystemNameTo")
    private String swSystemNameTo;


    public Header() {
    }

    public Header(String version, LocalDateTime created, String swSystemNameFrom, String swSystemNameTo) {
        this.version = version;
        this.created = created;
        this.swSystemNameFrom = swSystemNameFrom;
        this.swSystemNameTo = swSystemNameTo;
    }

    public String getVersion() {
        return version;
    }

    public Header setVersion(String version) {
        this.version = version;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Header setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public String getSwSystemNameFrom() {
        return swSystemNameFrom;
    }

    public Header setSwSystemNameFrom(String swSystemNameFrom) {
        this.swSystemNameFrom = swSystemNameFrom;
        return this;
    }

    public String getSwSystemNameTo() {
        return swSystemNameTo;
    }

    public Header setSwSystemNameTo(String swSystemNameTo) {
        this.swSystemNameTo = swSystemNameTo;
        return this;
    }
}
