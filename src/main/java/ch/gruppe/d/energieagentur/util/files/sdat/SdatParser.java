package ch.gruppe.d.energieagentur.util.files.sdat;

import ch.gruppe.d.energieagentur.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * This class parses the Sdat data
 */
public class SdatParser {
    private final Document document;

    /**
     * Constructor with file parameter
     * saves the important data
     *
     * @param file given file
     */
    public SdatParser(File file) throws IllegalArgumentException {
        //Sets Up the DocumentBuilderFactory and dataElement
        try {

            // Create a File object with the specified file path
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(file);
            document.getDocumentElement().normalize();

            // Retrieve a list of MeteringData elements
            NodeList dataList = document.getElementsByTagName("rsm:MeteringData");

            // Get the first MeteringData element
            dataElement = (Element) dataList.item(0);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //dataElement for reference in xml file
    private final Element dataElement;

    /**
     * Gets The DocumentID from the sdat file
     *
     * @return document id
     */
    public String getDocumentID() {
        //DocumentID
        String documentId = ((Element) (
                (Element) document.getElementsByTagName("rsm:ValidatedMeteredData_HeaderInformation").item(0)
        )
                .getElementsByTagName("rsm:InstanceDocument").item(0))
                .getElementsByTagName("rsm:DocumentID").item(0).getTextContent();
        return documentId.substring(documentId.length() - 3);
    }

    /**
     * Gets The Observations from the sdat file
     *
     * @return obsevations
     */
    public Map<Integer, BigDecimal> getObservation() {
        //Observation

        //String is Volume and Integer is Sequence
        Map<Integer, BigDecimal> observations = new HashMap<>();

        // Retrieve a list of Observation elements within the MeteringData
        NodeList oList = dataElement.getElementsByTagName("rsm:Observation");


        for (int i = 0; i < oList.getLength(); i++) {
            Element oElement = (Element) oList.item(i);
            observations.put(Integer.parseInt(oElement.getElementsByTagName("rsm:Sequence").item(0).getTextContent()), new BigDecimal(oElement.getElementsByTagName("rsm:Volume").item(0).getTextContent()));
        }
        return observations;
    }

    /**
     * Gets The Resolution from the sdat file
     *
     * @return resolution
     */
    public String getResolution() {
        //resolution
        // Retrieve the Sender element within the HeaderInformation
        Element resElement = (Element) dataElement.getElementsByTagName("rsm:Resolution").item(0);
        String resolution = resElement.getElementsByTagName("rsm:Resolution").item(0).getTextContent();
        String timeUnit = resElement.getElementsByTagName("rsm:Unit").item(0).getTextContent();

        return resolution + timeUnit;
    }


    /**
     * Gets The Interval from the sdat file for use in getIntervalStartTime and getIntervalEndTime
     *
     * @return interval from the sdat file
     */
    private Element getInterval() {
        //Interval
        return (Element) dataElement.getElementsByTagName("rsm:Interval").item(0);
    }

    /**
     * Gets The Interval StartTime from the sdat file
     *
     * @return starttime interval
     */
    public LocalDateTime getIntervalStartTime() {
        //StartDateTime
        return LocalDateTime.parse(getInterval().getElementsByTagName("rsm:StartDateTime").item(0).getTextContent(), DateTimeFormatter.ofPattern(Config.SDAT_DATE_FORMAT));
    }

    /**
     * Gets The Interval EndTime from the sdat file
     *
     * @return endtime interval
     */
    public LocalDateTime getIntervalEndTime() {
        //EndDateTime
        return LocalDateTime.parse(getInterval().getElementsByTagName("rsm:EndDateTime").item(0).getTextContent(), DateTimeFormatter.ofPattern(Config.SDAT_DATE_FORMAT));
    }

}
