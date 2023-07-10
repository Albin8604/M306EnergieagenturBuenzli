package ch.gruppe.d.energieagentur.util.files;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalDate;

/**
 * This interface is used as an FileManager
 */
public interface FileManager {
    /**
     * Read the data from the given folder by date
     *
     * @param folder  given folder
     * @param from    from date
     * @param to      to date
     * @param readAll if it should ignore the dates and read all
     * @throws JAXBException
     */
    default void readFolder(File folder, LocalDate from, LocalDate to, boolean readAll) throws JAXBException {
        throw new UnsupportedOperationException();
    }
}
