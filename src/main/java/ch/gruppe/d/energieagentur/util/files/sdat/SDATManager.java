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
    public static Map<LocalDateTime, BigDecimal> produced;
    public static Map<LocalDateTime, BigDecimal> purchased;

    private boolean isKwH = true;

    /**
     * Creates Sdat objects from files in given folder
     *
     * @param sdatFolder given Sdat folder
     */
    @Override
    public void readFolder(File sdatFolder, LocalDate from, LocalDate to, boolean readAll) throws IllegalArgumentException {
        if (sdatFolder == null || !sdatFolder.isDirectory() || sdatFolder.listFiles() == null || Objects.requireNonNull(sdatFolder.listFiles()).length == 0) {
            throw new IllegalArgumentException("Error reading folder");
        }

        File[] files = sdatFolder.listFiles();

        if (to != null) {
            to = to.plusDays(1);
        }

        //clearing maps before filling them
        produced = new TreeMap<>();
        purchased = new TreeMap<>();

        //going through all files
        for (File file : files) {
            if (file.getName().toUpperCase().contains("DS_Store".toUpperCase())){
                continue;
            }

            SdatParser sdatParser = new SdatParser(file);

            //setting first parameters to be able to check if this file can be skipped
            final boolean isPurchased = sdatParser.getDocumentID().equalsIgnoreCase("742");
            final LocalDateTime start = sdatParser.getIntervalStartTime();
            final LocalDateTime end = sdatParser.getIntervalEndTime();

            if (!readAll && (start.isBefore(from.atStartOfDay().minusMonths(1)) || end.isAfter(to.atStartOfDay().plusMonths(1)))) {
                continue;
            }

            final int resolutionInMin = convertToMin(sdatParser.getResolution());

            int count = 1;

            //filling the data
            for (Map.Entry<Integer, BigDecimal> observation : sdatParser.getObservation().entrySet()) {

                final LocalDateTime key = start.plusMinutes((long) resolutionInMin * count);
                BigDecimal value = observation.getValue();

                if (!readAll && (key.isBefore(from.atStartOfDay()) || key.isAfter(to.atStartOfDay()))) {
                    continue;
                }

                if (!isKwH()) {
                    //Calculating the minutes into hours and calculating Kw from KwH
                    value = value.divide(
                            BigDecimal.valueOf(1d / 60 * ((long) resolutionInMin * count)),
                            RoundingMode.HALF_UP
                    );
                }

                count++;

                try {
                    if (isPurchased) {
                        purchased.put(key, value);
                    } else {
                        produced.put(key, value);
                    }
                } catch (NullPointerException e) {
                    return;
                }


            }
        }
    }

    /**
     * If unit isn't MIN it gets converted into Minutes
     *
     * @param interval given interval
     * @return the value in minutes
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
     * Extracts the unit from given interval
     *
     * @param interval given interval
     * @return unit of interval
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
