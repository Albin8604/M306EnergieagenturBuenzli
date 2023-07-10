package ch.gruppe.d.energieagentur.util.files.chooser;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ChooserManager {

    /**
     * Opens a file chooser dialog and returns the selected file
     *
     * @param stage      stage to open the dialog on
     * @param extensions extensions to filter for
     * @return selected file
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
     * Opens a file chooser dialog and returns the selected file
     *
     * @param stage      stage to open the dialog on
     * @param extensions extensions to filter for
     * @return selected file
     */
    public static File getChoosedOpenFile(Stage stage, Extensions... extensions) {
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

    /**
     * Opens a directory chooser dialog and returns the selected directory
     *
     * @param stage stage to open the dialog on
     * @return selected directory
     */
    public static File getChoosedOpenFolder(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File file = directoryChooser.showDialog(stage);

        if (file == null) {
            throw new RuntimeException("File could not be opened");
        }

        return file;
    }

    /**
     * Opens a file chooser dialog and returns the selected files
     *
     * @param stage      stage to open the dialog on
     * @param extensions extensions to filter for
     * @return selected files
     */
    public static List<File> getChoosedOpenFiles(Stage stage, Extensions... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                Arrays.stream(extensions)
                        .map(Extensions::getExtensionFilter
                        ).toList()
        );

        List<File> file = fileChooser.showOpenMultipleDialog(stage);

        if (file == null) {
            throw new RuntimeException("Files could not be opened");
        }

        return file;
    }
}
