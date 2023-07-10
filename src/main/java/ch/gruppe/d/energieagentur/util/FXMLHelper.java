package ch.gruppe.d.energieagentur.util;

import ch.gruppe.d.energieagentur.assets.Assets;
import ch.gruppe.d.energieagentur.controller.Controller;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * This class helps with displaying the fxml
 */
public class FXMLHelper {
    /**
     * loads the fxml and calls the init method from its controller
     * @param asset the fxml
     * @return the object from the fxml
     * @param <T> what kind of object to expect
     */
    public static <T> T load(Assets asset) {
        FXMLLoader fxmlLoader = new FXMLLoader(asset.asUrl());
        T result;

        try {
            result = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();

            if (controller != null) {
                controller.init();
            }
        } catch (IOException | UnsupportedOperationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }
}