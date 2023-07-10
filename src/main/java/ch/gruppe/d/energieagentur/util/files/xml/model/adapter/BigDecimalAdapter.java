package ch.gruppe.d.energieagentur.util.files.xml.model.adapter;

import ch.gruppe.d.energieagentur.Config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * This class is used as an Adapter to be able to use BigDecimal with the xml parsing
 */
public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {
    @Override
    public BigDecimal unmarshal(String s) {
        return new BigDecimal(s);
    }

    @Override
    public String marshal(BigDecimal bigDecimal) {
        return new DecimalFormat(Config.DECIMAL_FORMAT).format(bigDecimal);
    }
}
