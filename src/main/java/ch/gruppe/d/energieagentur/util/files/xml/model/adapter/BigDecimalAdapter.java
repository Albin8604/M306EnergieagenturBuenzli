package ch.gruppe.d.energieagentur.util.files.xml.model.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * This class is used as an Adapter to be able to use BigDecimal with the xml parsing
 */
public class BigDecimalAdapter extends XmlAdapter<String, BigDecimal> {

    /**
     * Unmarshals a BigDecimal
     *
     * @param s String
     * @return BigDecimal
     * @throws Exception Exception
     */
    @Override
    public BigDecimal unmarshal(String s) throws Exception {
        return new BigDecimal(s);
    }

    /**
     * Marshals a BigDecimal
     *
     * @param bigDecimal BigDecimal
     * @return String
     * @throws Exception Exception
     */
    @Override
    public String marshal(BigDecimal bigDecimal) throws Exception {
        return new DecimalFormat(Config.DECIMAL_FORMAT).format(bigDecimal);
    }
}
