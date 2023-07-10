package ch.gruppe.d.energieagentur.util.files;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a CSV file manager
 */
public class JSONFileManager<T> implements ExportFileManager<T> {

    private final Class<T> tClass;

    /**
     * Constructor
     *
     * @param tClass Class<T>
     */
    public JSONFileManager(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Write a list of objects to a csv file
     *
     * @param file String
     */
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

    /**
     * Write a list of objects to a csv file
     *
     * @param data     List<T>
     * @param filePath String
     */
    @Override
    public void write(List<T> data, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get content from file as string
     *
     * @param file File
     * @return String
     * @throws IOException IOException
     */
    private String getContentFromFileAsString(File file) throws IOException {
        List<String> allLines = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        Files.readAllLines(file.toPath());

        stringBuilder.append(allLines);

        return stringBuilder.toString();
    }
}
