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

    /**
     * Default constructor
     */
    public Header() {
    }

    /**
     * Constructor
     *
     * @param version          String
     * @param created          LocalDateTime
     * @param swSystemNameFrom String
     * @param swSystemNameTo   String
     */
    public Header(String version, LocalDateTime created, String swSystemNameFrom, String swSystemNameTo) {
        this.version = version;
        this.created = created;
        this.swSystemNameFrom = swSystemNameFrom;
        this.swSystemNameTo = swSystemNameTo;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getVersion() {
        return version;
    }

    /**
     * Constructor
     *
     * @param version String
     * @return Header
     */
    public Header setVersion(String version) {
        this.version = version;
        return this;
    }

    /**
     * Constructor
     *
     * @return LocalDateTime
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Constructor
     *
     * @param created LocalDateTime
     * @return Header
     */
    public Header setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getSwSystemNameFrom() {
        return swSystemNameFrom;
    }

    /**
     * Constructor
     *
     * @param swSystemNameFrom String
     * @return Header
     */
    public Header setSwSystemNameFrom(String swSystemNameFrom) {
        this.swSystemNameFrom = swSystemNameFrom;
        return this;
    }

    /**
     * Constructor
     *
     * @return String
     */
    public String getSwSystemNameTo() {
        return swSystemNameTo;
    }

    /**
     * Constructor
     *
     * @param swSystemNameTo String
     * @return Header
     */
    public Header setSwSystemNameTo(String swSystemNameTo) {
        this.swSystemNameTo = swSystemNameTo;
        return this;
    }


}
