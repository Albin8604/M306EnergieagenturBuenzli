package ch.gruppe.d.energieagentur.util.files;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalDate;

public interface FileManager {
    default void readFolder(File eslFolder, LocalDate from, LocalDate to) throws JAXBException{
        throw new UnsupportedOperationException();
    };

}
