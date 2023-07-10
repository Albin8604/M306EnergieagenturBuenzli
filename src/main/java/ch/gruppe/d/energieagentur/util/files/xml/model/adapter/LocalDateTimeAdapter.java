package ch.gruppe.d.energieagentur.util.files.xml.model.adapter;

import ch.gruppe.d.energieagentur.Config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is used as an Adapter to be able to use LocalDateTime with the xml parsing
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    /**
     * Unmarshals a LocalDateTime
     *
     * @param s String
     * @return LocalDateTime
     * @throws Exception Exception
     */
    @Override
    public LocalDateTime unmarshal(String s) {
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(Config.ESL_DATE_FORMAT));
    }

    /**
     * Marshals a LocalDateTime
     *
     * @param localDateTime LocalDateTime
     * @return String
     * @throws Exception Exception
     */
    @Override
    public String marshal(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(Config.ESL_DATE_FORMAT));
    }
}
