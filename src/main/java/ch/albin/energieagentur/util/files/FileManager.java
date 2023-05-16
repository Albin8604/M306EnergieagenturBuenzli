package ch.albin.energieagentur.util.files;

import java.io.File;
import java.util.List;

public interface FileManager<T> {
    List<T> read(File file);

    void write(List<T> data, String path);

}
