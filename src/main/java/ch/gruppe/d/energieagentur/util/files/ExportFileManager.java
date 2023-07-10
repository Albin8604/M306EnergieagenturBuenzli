package ch.gruppe.d.energieagentur.util.files;

import java.io.File;
import java.util.List;

/**
 * This class is a CSV file manager
 */
public interface ExportFileManager<T> {
    List<T> read(File file);

    void write(List<T> data, String filePath);
}
