package ch.gruppe.d.energieagentur.util.files.export;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Generic JSONFileManager used for Managing JSON Files as reading and writing
 *
 * @param <T>
 */
public class JSONFileManager<T> implements ExportFileManager<T> {

    private final Class<T> tClass;

    public JSONFileManager(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public List<T> read(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> objList = null;

        try {
            byte[] jsonData = Files.readAllBytes(
                    file.toPath()
            );

            objList = objectMapper.readValue(jsonData, objectMapper.getTypeFactory().constructCollectionType(List.class, tClass));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return objList;
    }

    @Override
    public void write(List<T> data, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
