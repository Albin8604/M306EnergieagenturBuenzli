package ch.gruppe.d.energieagentur.util;

import ch.albin.energieagentur.sdatParseTest.SdatParser;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static ch.gruppe.d.energieagentur.util.files.xml.model.adapter.Config.MAX_ENTRIES_ON_DIAGRAM;

/**
 * This class is used to manage all the Sdat data
 */
public class SDATManager {
    public static final Map<LocalDateTime, BigDecimal> PRODUCED = new TreeMap<>();
    public static final Map<LocalDateTime, BigDecimal> PURCHASED = new TreeMap<>();

    /**
     * Creates Sdat objects from files in given folder
     * @param sdatFolder given Sdat folder
     */
    public void readFolder(File sdatFolder, LocalDate from, LocalDate to){
        if (sdatFolder == null || !sdatFolder.isDirectory() || sdatFolder.listFiles() == null || Objects.requireNonNull(sdatFolder.listFiles()).length == 0){
            throw new RuntimeException("Error reading folder");
        }
        File[] files = sdatFolder.listFiles();

        //clearing maps before filling them
        PRODUCED.clear();
        PURCHASED.clear();

        for (File file : files) {
            SdatParser sdatParser = new SdatParser(file);

            final boolean isPurchased = sdatParser.getDocumentID().equalsIgnoreCase("742");
            final LocalDateTime start = sdatParser.getIntervalStartTime();
            final LocalDateTime end = sdatParser.getIntervalEndTime();

            if (start.isBefore(from.atStartOfDay()) || end.isAfter(to.atStartOfDay())){
                continue;
            }

            final int resolutionInMin = convertToMin(sdatParser.getResolution());

            int count = 1;

            for (Map.Entry<Integer, BigDecimal> observation : sdatParser.getObservation().entrySet()) {

                final LocalDateTime key = start.plusMinutes((long) resolutionInMin *count++);
                final BigDecimal value = observation.getValue();

                if (isPurchased){
                    PURCHASED.put(key,value);
                }else {
                    PRODUCED.put(key,value);
                }
            }

            //todo
            if (PURCHASED.size() > MAX_ENTRIES_ON_DIAGRAM ||
                    PRODUCED.size() > MAX_ENTRIES_ON_DIAGRAM){

            }
        }
    }

    private int convertToMin(String interval){
        String unit = extractUnit(interval);
        String value = interval.replaceAll(unit,"");

        if (unit.equalsIgnoreCase("MIN")) {
            return Integer.parseInt(value);
        }

        throw new RuntimeException("Unit "+unit+" not supported");
    }

    private String extractUnit(String interval){
        char[] chars = interval.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                return interval.substring(i);
            }
        }

        throw new RuntimeException("Couldn't extract unit");
    }
}
