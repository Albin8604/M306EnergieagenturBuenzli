package ch.gruppe.d.energieagentur.util.files.xml;

import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ESL;
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
        JAXBContext context = JAXBContext.newInstance(ESL.class);
        Unmarshaller mar = context.createUnmarshaller();
        ESL esl = (ESL) mar.unmarshal(eslFile);

        Assertions.assertNotNull(esl.getHeader());
        Assertions.assertNotNull(esl.getMeter());

        Assertions.assertNotNull(esl.getHeader().getCreated());

        Assertions.assertNotEquals(0, esl.getMeter().getTimePeriodList().size());
        Assertions.assertNotEquals(0, esl.getMeter().getTimePeriodList().get(0).getValueRowList().size());
        Assertions.assertNotNull(esl.getMeter().getTimePeriodList().get(0).getValueRowList().get(0).getValue());
        Assertions.assertNotNull(esl.getMeter().getTimePeriodList().get(0).getValueRowList().get(0).getValueTimeStamp());

        Assertions.assertEquals(2019, esl.getMeter().getTimePeriodList().get(0).getValueRowList().get(0).getValueTimeStamp().getYear());

        Assertions.assertNull(esl.getMeter().getTimePeriodList().get(0).getValueRowList().get(2).getValueTimeStamp());
    }
}
