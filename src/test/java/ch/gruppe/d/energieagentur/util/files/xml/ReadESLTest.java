package ch.gruppe.d.energieagentur.util.files.xml;

import ch.gruppe.d.energieagentur.StarterStarter;
import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ESLBillingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;
/**
 * This class is used to test the ESL xml parsing
 */
public class ReadESLTest {
    private static File eslFile;

    @BeforeAll
    public static void setup() throws URISyntaxException {
       eslFile = new File(Objects.requireNonNull(ReadESLTest.class.getClassLoader().getResource("ESL-Files/EdmRegisterWertExport_20190503_eslevu_20190503050535.xml")).toURI());
    }
    @Test
    public void readESLFile() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ESLBillingData.class);
        Unmarshaller mar = context.createUnmarshaller();
        ESLBillingData eslBillingData = (ESLBillingData) mar.unmarshal(eslFile);

        Assertions.assertNotNull(eslBillingData.getHeader());
        Assertions.assertNotNull(eslBillingData.getMeter());

        Assertions.assertNotNull(eslBillingData.getHeader().getCreated());

        Assertions.assertNotEquals(0,eslBillingData.getMeter().getTimePeriodList().size());
        Assertions.assertNotEquals(0,eslBillingData.getMeter().getTimePeriodList().get(0).getValueRowList().size());
        Assertions.assertNotNull(eslBillingData.getMeter().getTimePeriodList().get(0).getValueRowList().get(0).getValue());
        Assertions.assertNotNull(eslBillingData.getMeter().getTimePeriodList().get(0).getValueRowList().get(0).getValueTimeStamp());

        Assertions.assertEquals(2019,eslBillingData.getMeter().getTimePeriodList().get(0).getValueRowList().get(0).getValueTimeStamp().getYear());

        Assertions.assertNull(eslBillingData.getMeter().getTimePeriodList().get(0).getValueRowList().get(2).getValueTimeStamp());
    }
}
