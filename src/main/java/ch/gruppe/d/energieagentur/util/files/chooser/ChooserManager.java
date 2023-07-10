package ch.gruppe.d.energieagentur.util.files.chooser;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to manage the chooser in javafx
 */
public class ChooserManager {
    /**
     * Opens the file chooser dialog with given extensions and returns the selected file
     * @param stage the stage on which the dialog should be shown
     * @param extensions extensions of the file
     * @return chosen file
     */
    public static File getChoosedSaveFile(Stage stage, Extensions... extensions) {
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

    /**
     * Opens the folder chooser dialog and returns the selected folder
     * @param stage the stage on which the dialog should be shown
     * @return chosen folder
     */
    public static File getChoosedOpenFolder(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File file = directoryChooser.showDialog(stage);

        if (file == null) {
            throw new RuntimeException("File could not be opened");
        }

        return file;
    }
}