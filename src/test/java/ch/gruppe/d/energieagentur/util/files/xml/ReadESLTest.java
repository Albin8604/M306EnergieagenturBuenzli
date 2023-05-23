package ch.gruppe.d.energieagentur.util.files.xml;

import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ESLBillingData;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * This class is used to test the ESL xml parsing
 */
public class ReadESLTest {
    private static final File ESL_FILE = new File("/Users/albin/IdeaProjects/M306EnergieagenturBuenzli/src/main/resources/ESL-Files/EdmRegisterWertExport_20190503_eslevu_20190503050535.xml");

    @Test
    public void readESLFile() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ESLBillingData.class);
        Unmarshaller mar = context.createUnmarshaller();
        ESLBillingData eslBillingData = (ESLBillingData) mar.unmarshal(ESL_FILE);

        //TODO

        System.out.println("mememe");
    }
}
