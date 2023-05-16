package ch.albin.energieagentur.util.files.chooser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

public class FileChooserManager {
    public static <T> File getChoosedSaveFile(Stage stage, Extensions... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                Arrays.stream(extensions)
                        .map(Extensions::getExtensionFilter
                        ).toList()
        );

        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            throw new RuntimeException("File not saved");
        }

        return file;
    }

    public static <T> File getChoosedOpenFile(Stage stage, Extensions... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                Arrays.stream(extensions)
                        .map(Extensions::getExtensionFilter
                        ).toList()
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            throw new RuntimeException("File could not be opened");
        }

        return file;
    }
}
