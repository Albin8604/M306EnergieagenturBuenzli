package ch.albin.energieagentur.sdatParseTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SdatParser {

    public SdatParser(String filename) {
        //Sets Up the DocumentBuilderFactory and dataElement
        try {
            // String filePath = "src/main/resources/SDAT-Files/20190313_093127_12X-0000001216-O_E66_12X-LIPPUNEREM-T_ESLEVU121963_-279617263.xml";

            // Create a File object with the specified file path
            File file = new File("src/main/resources/SDAT-Files/"+filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            // Retrieve a list of MeteringData elements
            NodeList dataList = document.getElementsByTagName("rsm:MeteringData");

            // Get the first MeteringData element
            dataElement = (Element) dataList.item(0);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    //dataElement for reference in xml file
    private final Element dataElement;

    //Gets The DocumentID from the sdat file
    public String getDocumentID() {
        //DocumentID
        return dataElement.getElementsByTagName("rsm:DocumentID").item(0).getTextContent();
    }

    //Gets The Observations from the sdat file
    public Map<Integer,Double> getObservation() {
        //Observation

        //String is Volume and Integer is Sequence
        Map<Integer, Double> observations = new HashMap<>();

        // Retrieve a list of Observation elements within the MeteringData
        NodeList oList = dataElement.getElementsByTagName("rsm:Observation");


        for (int i = 0; i < oList.getLength(); i++) {
            Element oElement = (Element) oList.item(i);
            observations.put(Integer.parseInt(oElement.getElementsByTagName("rsm:Sequence").item(0).getTextContent()), Double.parseDouble(oElement.getElementsByTagName("rsm:Volume").item(0).getTextContent()));
        }
        return observations;
    }

    //Gets The Resolution from the sdat file
    public String getResolution() {
        //resolution
        // Retrieve the Sender element within the HeaderInformation
        Element resElement = (Element) dataElement.getElementsByTagName("rsm:Resolution").item(0);
        String resolution = resElement.getElementsByTagName("rsm:Resolution").item(0).getTextContent();
        String timeUnit = resElement.getElementsByTagName("rsm:Unit").item(0).getTextContent();

        return resolution + timeUnit;
    }

    //Gets The Interval from the sdat file for use in getIntervalStartTime and getIntervalEndTime
    private Element getInterval() {
        //Interval
        return (Element) dataElement.getElementsByTagName("rsm:Interval").item(0);
    }

    //Gets The Interval StartTime from the sdat file
    public String getIntervalStartTime() {
        //StartDateTime
        return getInterval().getElementsByTagName("rsm:StartDateTime").item(0).getTextContent();
    }

    //Gets The Interval EndTime from the sdat file
    public String getIntervalEndTime() {
        //EndDateTime
        return getInterval().getElementsByTagName("rsm:EndDateTime").item(0).getTextContent();
    }

}
