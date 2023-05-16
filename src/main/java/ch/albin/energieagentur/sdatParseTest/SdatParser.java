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
        String filePath = "C:\\Users\\jirof\\gitprojects\\M306EnergieagenturBuenzli\\src\\main\\resources\\SDAT-Files\\20190313_093127_12X-0000001216-O_E66_12X-LIPPUNEREM-T_ESLEVU121963_-279617263.xml";
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            System.out.println("Root Element: " + document.getDocumentElement().getNodeName());

            NodeList infoList = document.getElementsByTagName("rsm:ValidatedMeteredData_HeaderInformation");
            NodeList dataList = document.getElementsByTagName("rsm:MeteringData");

            System.out.println("----------------------------");

            Element dataElement = (Element) dataList.item(0);
            Element infoElement = (Element) infoList.item(0);

            Element sElement = (Element) infoElement.getElementsByTagName("rsm:Sender").item(0);
            System.out.println("senderID: " + sElement.getElementsByTagName("rsm:ID").item(0).getTextContent());
            Element rElement = (Element) infoElement.getElementsByTagName("rsm:Receiver").item(0);
            System.out.println("receiverID: " + rElement.getElementsByTagName("rsm:ID").item(0).getTextContent());

            NodeList oList = dataElement.getElementsByTagName("rsm:Observation");
            String unit = dataElement.getElementsByTagName("rsm:MeasureUnit").item(0).getTextContent();
            for (int i = 0; i < oList.getLength(); i++) {
                Element oElement = (Element) oList.item(i);
                System.out.println("Observation: seq: " + oElement.getElementsByTagName("rsm:Sequence").item(0).getTextContent() + ", Volume: " + oElement.getElementsByTagName("rsm:Volume").item(0).getTextContent() + unit);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}