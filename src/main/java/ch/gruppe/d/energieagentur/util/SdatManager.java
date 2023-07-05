package ch.gruppe.d.energieagentur.util;

import ch.albin.energieagentur.sdatParseTest.SdatParser;
import ch.gruppe.d.energieagentur.model.Sdat;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * This class is used to manage all the Sdat data
 */
public class SdatManager {

    private List<Sdat> produced;
    private List<Sdat> purchased;

    /**
     * Creates Sdat objects from files in given folder
     * @param sdatFolder given Sdat folder
     */
    public void readFolder(File sdatFolder){
        if (sdatFolder == null || !sdatFolder.isDirectory() || sdatFolder.listFiles() == null || sdatFolder.listFiles().length <= 0){
            throw new RuntimeException("Error reading folder");
        }
        File[] files = sdatFolder.listFiles();
        for (File file : files) {
            SdatParser sdatParser = new SdatParser(file);
            Sdat sdat = new Sdat();

            sdat.setPurchased(sdatParser.getDocumentID().equalsIgnoreCase("742"));
            sdat.setStart(sdatParser.getIntervalStartTime());
            sdat.setEnd(sdatParser.getIntervalEndTime());
            sdat.setIntervalMin(convertToMin(sdatParser.getResolution()));

            for (Map.Entry<Integer, BigDecimal> observations : sdatParser.getObservation().entrySet()) {
                sdat.addVolume(observations.getValue());
            }

            if (sdat.isPurchased()){
                purchased.add(sdat);
            }else {
                produced.add(sdat);
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
