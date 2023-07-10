package ch.gruppe.d.energieagentur.util.files.export;

import java.io.File;
import java.util.List;

/**
 * This interface is use for the ExportFileManager
 *
 * @param <T> exports of the object T
 */
public interface ExportFileManager<T> {
    /**
     * Read the given file and returns a list of T objects
     *
     * @param file given file
     * @return list of T objects
     */
    List<T> read(File file);

    /**
     * Writes the given data to the given path
     *
     * @param data     given data
     * @param filePath given path
     */
    void write(List<T> data, String filePath);
}
