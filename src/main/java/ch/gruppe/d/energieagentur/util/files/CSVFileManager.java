package ch.gruppe.d.energieagentur.util.files;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

//https://www.baeldung.com/opencsv
public class CSVFileManager<T> implements ExportFileManager<T> {
    private static final char SEPARATOR = ',';
    private final Class<T> tClass;

    /**
     * Constructor
     *
     * @param tClass Class<T>
     */
    public CSVFileManager(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Write a list of objects to a csv file
     *
     * @param file String
     */
    @Override
    public List<T> read(File file) {
        List<T> objList = new ArrayList<>();

        CsvToBeanBuilder<T> beanBuilder;
        try {
            beanBuilder = new CsvToBeanBuilder<>(
                    new InputStreamReader(new FileInputStream(file))
            );

            beanBuilder.withType(tClass);
            beanBuilder.withSeparator(SEPARATOR);

            // build methods returns a list of Beans

            beanBuilder.build().parse().forEach(e -> {
                System.out.println(e);
                objList.add(e);
            });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return objList;
    }

    /**
     * Write a list of objects to a csv file
     *
     * @param data     List<T>
     * @param fileName String
     */
    @Override
    public void write(List<T> data, String fileName) {

        try (Writer writer = new FileWriter(fileName)) {

            StatefulBeanToCsv<T> statefulBeanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withQuotechar('\"')
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            statefulBeanToCsv.write(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        } catch (CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }

    }
}
