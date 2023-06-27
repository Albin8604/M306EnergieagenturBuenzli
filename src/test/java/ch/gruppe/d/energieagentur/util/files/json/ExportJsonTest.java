package ch.gruppe.d.energieagentur.util.files.json;

import ch.gruppe.d.energieagentur.model.export.json.Export;
import ch.gruppe.d.energieagentur.model.export.json.SensorId;
import ch.gruppe.d.energieagentur.model.export.json.Zaehlerstand;
import ch.gruppe.d.energieagentur.util.files.JSONFileManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class ExportJsonTest {
    private static final String SAVE_EXPORT_TO ="/Users/albin/Downloads/jsonExportTest.json";

    private static final List<Export> EXPORT_TEST_DATA = List.of(
            new Export(
                    SensorId.ID735,
                    new Zaehlerstand[]{
                            new Zaehlerstand(LocalDateTime.now(),5345345345.40)
                    }
            ),new Export(
                    SensorId.ID742,
                    new Zaehlerstand[]{
                            new Zaehlerstand(LocalDateTime.now().minusDays(500),43433.44)
                    }
            ),new Export(
                    SensorId.ID735,
                    new Zaehlerstand[]{
                            new Zaehlerstand(LocalDateTime.now().minusYears(5),23.1)
                    }
            )
    );

    private static final JSONFileManager<Export> JSON_FILE_MANAGER = new JSONFileManager<>(Export.class);

    @Test
    public void exportJsonTest(){
        JSON_FILE_MANAGER.write(EXPORT_TEST_DATA,SAVE_EXPORT_TO);
        //MANUAL VALIDATION NEEDED
    }
}
