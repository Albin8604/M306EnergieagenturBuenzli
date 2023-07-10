package ch.gruppe.d.energieagentur.util.files.export;

import java.io.File;
import java.util.List;

public interface ExportFileManager<T> {
    List<T> read(File file);

    void write(List<T> data, String filePath);
}
