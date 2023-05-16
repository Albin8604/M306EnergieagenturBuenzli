package ch.albin.energieagentur.sdatParseTest;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SdatParser {

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        // Path to the SDAT XML file
        String filePath = "src/main/resources/SDAT-Files/20190313_093127_12X-0000001216-O_E66_12X-LIPPUNEREM-T_ESLEVU121963_-279617263.xml";
        try {
            // Create a File object with the specified file path
            File file = new File(filePath);

            // Create a DocumentBuilderFactory
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

            // Create a DocumentBuilder
            DocumentBuilder db = dbf.newDocumentBuilder();

            // Parse the XML file and obtain the Document object
            Document document = db.parse(file);

            // Normalize the document to ensure consistent handling of XML data
            document.getDocumentElement().normalize();

            // Print the root element name
            System.out.println("Root Element: " + document.getDocumentElement().getNodeName());

            // Retrieve a list of ValidatedMeteredData_HeaderInformation elements
            NodeList infoList = document.getElementsByTagName("rsm:ValidatedMeteredData_HeaderInformation");

            // Retrieve a list of MeteringData elements
            NodeList dataList = document.getElementsByTagName("rsm:MeteringData");

            System.out.println("----------------------------");

            // Get the first MeteringData element
            Element dataElement = (Element) dataList.item(0);

            // Get the first ValidatedMeteredData_HeaderInformation element
            Element infoElement = (Element) infoList.item(0);

            // Retrieve the Sender element within the HeaderInformation
            Element sElement = (Element) infoElement.getElementsByTagName("rsm:Sender").item(0);
            System.out.println("senderID: " + sElement.getElementsByTagName("rsm:ID").item(0).getTextContent());

            // Retrieve the Receiver element within the HeaderInformation
            Element rElement = (Element) infoElement.getElementsByTagName("rsm:Receiver").item(0);
            System.out.println("receiverID: " + rElement.getElementsByTagName("rsm:ID").item(0).getTextContent());

            // Retrieve a list of Observation elements within the MeteringData
            NodeList oList = dataElement.getElementsByTagName("rsm:Observation");

            // Get the unit of measurement for the data
            String unit = dataElement.getElementsByTagName("rsm:MeasureUnit").item(0).getTextContent();

            // Iterate over the Observation elements and print their details
            for (int i = 0; i < oList.getLength(); i++) {
                Element oElement = (Element) oList.item(i);
                System.out.println("Observation: seq: " + oElement.getElementsByTagName("rsm:Sequence").item(0).getTextContent() + ", Volume: " + oElement.getElementsByTagName("rsm:Volume").item(0).getTextContent() + unit);
            }
        } catch (IOException e) {
            // Handle IOException by printing the error message
            System.out.println(e);
        }
    }
}
