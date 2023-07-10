package ch.gruppe.d.energieagentur.util.files.sdat;

import ch.gruppe.d.energieagentur.util.files.FileManager;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This class is used to manage all the Sdat data
 */
public class SDATManager implements FileManager {
    public static final Map<LocalDateTime, BigDecimal> PRODUCED = new TreeMap<>();
    public static final Map<LocalDateTime, BigDecimal> PURCHASED = new TreeMap<>();

    private boolean isKwH = true;

    /**
     * Creates Sdat objects from files in given folder
     *
     * @param sdatFolder given Sdat folder
     */
    public void readFolder(File sdatFolder, LocalDate from, LocalDate to) {
        if (sdatFolder == null || !sdatFolder.isDirectory() || sdatFolder.listFiles() == null || Objects.requireNonNull(sdatFolder.listFiles()).length == 0) {
            throw new RuntimeException("Error reading folder");
        }
        File[] files = sdatFolder.listFiles();

        to = to.plusDays(1);

        //clearing maps before filling them
        PRODUCED.clear();
        PURCHASED.clear();

        for (File file : files) {
            SdatParser sdatParser = new SdatParser(file);

            final boolean isPurchased = sdatParser.getDocumentID().equalsIgnoreCase("742");
            final LocalDateTime start = sdatParser.getIntervalStartTime();
            final LocalDateTime end = sdatParser.getIntervalEndTime();

            if (start.isBefore(from.atStartOfDay().minusMonths(1)) || end.isAfter(to.atStartOfDay().plusMonths(1))) {
                continue;
            }

            final int resolutionInMin = convertToMin(sdatParser.getResolution());

            int count = 1;

            for (Map.Entry<Integer, BigDecimal> observation : sdatParser.getObservation().entrySet()) {

                final LocalDateTime key = start.plusMinutes((long) resolutionInMin * count);
                BigDecimal value = observation.getValue();

                if (key.isBefore(from.atStartOfDay()) || key.isAfter(to.atStartOfDay())) {
                    continue;
                }

                if (!isKwH()) {
                    //Calculating the minutes into hours and calculating Kw from KwH
                    value = value.divide(
                            BigDecimal.valueOf(1d / 60 * ((long) resolutionInMin * count)),
                            RoundingMode.HALF_UP
                    );
                }

                if (isPurchased) {
                    PURCHASED.put(key, value);
                } else {
                    PRODUCED.put(key, value);
                }

                count++;
            }
        }
    }

    /**
     * Converts the given interval to minutes
     *
     * @param interval given interval
     * @return interval in minutes
     */
    private int convertToMin(String interval) {
        String unit = extractUnit(interval);
        String value = interval.replaceAll(unit, "");

        if (unit.equalsIgnoreCase("MIN")) {
            return Integer.parseInt(value);
        }

        throw new RuntimeException("Unit " + unit + " not supported");
    }

    /**
     * Extracts the unit from the given interval
     *
     * @param interval given interval
     * @return unit
     */
    private String extractUnit(String interval) {
        char[] chars = interval.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                return interval.substring(i);
            }
        }

        throw new RuntimeException("Couldn't extract unit");
    }

    /**
     * gets the value of isKwH
     */
    public boolean isKwH() {
        return isKwH;
    }

    /**
     * sets the value of isKwH
     *
     * @return SDATManager
     */
    public SDATManager setKwH(boolean kwH) {
        isKwH = kwH;
        return this;
    }
}
