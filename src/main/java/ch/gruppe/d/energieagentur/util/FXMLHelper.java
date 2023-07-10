package ch.gruppe.d.energieagentur.util;

import ch.gruppe.d.energieagentur.assets.Assets;
import ch.gruppe.d.energieagentur.controller.Controller;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

/**
 * This class helps with displaying the fxml
 */
public class FXMLHelper {
    /**
     * loads the fxml and calls the init method from its controller
     *
     * @param asset the fxml
     * @param <T>   what kind of object to expect
     * @return the object from the fxml
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