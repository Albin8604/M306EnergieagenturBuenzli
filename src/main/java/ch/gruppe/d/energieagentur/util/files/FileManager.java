package ch.gruppe.d.energieagentur.util.files;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalDate;

/**
 * This class is a file manager
 */
public interface FileManager {

    /**
     * This method reads a file
     *
     * @param eslFolder File
     * @throws JAXBException JAXBException
     */
    default void readFolder(File eslFolder, LocalDate from, LocalDate to) throws JAXBException {
        throw new UnsupportedOperationException();
    }

    ;

}
