package ch.gruppe.d.energieagentur.util.files.esl;

import ch.gruppe.d.energieagentur.util.files.FileManager;
import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ESL;
import ch.gruppe.d.energieagentur.util.files.xml.model.esl.TimePeriod;
import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ValueRow;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * This class is used to Manage the ESL files
 */
public class ESLManager implements FileManager {

    /**
     * 1-1:1.8.1 (Bezug Hochtarif)
     * 1-1:1.8.2 (Bezug Niedertarif),
     * 1-1:2.8.1 (Einspeisung Hochtarif)
     * 1-1:2.8.2 (Einspeisung Niedertarif)
     */

    private static final String PURCHASED_HIGH_FARE_OBIS_CODE = "1-1:1.8.1";
    private static final String PURCHASED_LOW_FARE_OBIS_CODE = "1-1:1.8.2";
    private static final String PRODUCED_HIGH_FARE_OBIS_CODE = "1-1:2.8.1";
    private static final String PRODUCED_LOW_FARE_OBIS_CODE = "1-1:2.8.2";
    public static final Map<LocalDateTime, BigDecimal> PRODUCED = new TreeMap<>();
    public static final Map<LocalDateTime, BigDecimal> PURCHASED = new TreeMap<>();

    @Override
    public void readFolder(File eslFolder, LocalDate from, LocalDate to, boolean readAll) throws IllegalArgumentException, JAXBException {
        if (eslFolder == null || !eslFolder.isDirectory() || eslFolder.listFiles() == null ||
                Objects.requireNonNull(eslFolder.listFiles()).length == 0) {
            throw new IllegalArgumentException("Error reading folder");
        }

        //creating the instances to be able to read the ESL Files
        final JAXBContext context = JAXBContext.newInstance(ESL.class);
        final Unmarshaller mar = context.createUnmarshaller();

        //adds one day to the "to" parameter, for better filtering
        if (to != null){
            to = to.plusDays(1);
        }

        //clearing maps before filling them
        PRODUCED.clear();
        PURCHASED.clear();

        //reads all ESL files into ESL objects
        List<ESL> eslList = new ArrayList<>();

        for (File file : Objects.requireNonNull(eslFolder.listFiles())) {
            try {
                eslList.add((ESL) mar.unmarshal(file));
            } catch (JAXBException e) {
                throw new IllegalArgumentException(e);
            }
        }

        //Fills all the ESL data
        for (ESL esl : eslList) {
            for (TimePeriod timePeriod : esl.getMeter().getTimePeriodList()) {
                if (!readAll && (from.isAfter(timePeriod.getEnd().toLocalDate()) || to.isBefore(timePeriod.getEnd().toLocalDate()))) {
                    continue;
                }

                BigDecimal tempProducedValue = null;
                BigDecimal tempPurchasedValue = null;

                for (ValueRow valueRow : timePeriod.getValueRowList()) {
                    if (valueRow.getObis().equalsIgnoreCase(PRODUCED_HIGH_FARE_OBIS_CODE) ||
                            valueRow.getObis().equalsIgnoreCase(PRODUCED_LOW_FARE_OBIS_CODE)) {
                        tempProducedValue = addElementToMap(timePeriod.getEnd(), tempProducedValue, valueRow.getValue(), PRODUCED);
                    }
                    if (valueRow.getObis().equalsIgnoreCase(PURCHASED_HIGH_FARE_OBIS_CODE) ||
                            valueRow.getObis().equalsIgnoreCase(PURCHASED_LOW_FARE_OBIS_CODE)) {
                        tempPurchasedValue = addElementToMap(timePeriod.getEnd(), tempPurchasedValue, valueRow.getValue(), PURCHASED);
                    }
                }
            }
        }
    }

    /**
     * This method add the given value to the tempValue and then adds it in the DATA MAP
     * @param date given date
     * @param tempValue given tempValue
     * @param value given Value
     * @param map given Map
     * @return the new calculated value
     */
    private BigDecimal addElementToMap(LocalDateTime date, BigDecimal tempValue, BigDecimal value, Map<LocalDateTime, BigDecimal> map) {
        if (tempValue != null) {
            map.put(date, tempValue.add(value));
        }
        return value;
    }
}
