package ch.gruppe.d.energieagentur.util;

import ch.gruppe.d.energieagentur.assets.Assets;
import ch.gruppe.d.energieagentur.controller.Controller;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * This class is a helper for FXML files
 */
public class FXMLHelper {

    /**
     * This method loads a FXML file
     *
     * @param asset Assets
     * @param <T>   T
     * @return T
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

    /**
     * This method loads a FXML file
     *
     * @param asset Assets
     * @param <T>  T
     * @return T
     */
    public static <T> T loadModelWithoutController(Assets asset) {
        FXMLLoader fxmlLoader = new FXMLLoader(asset.asUrl());
        T result;

        try {
            result = fxmlLoader.load();
        } catch (IOException | UnsupportedOperationException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * This method loads a FXML file
     *
     * @param file String
     * @param <T>  T
     * @return T
     */
    public static <T> T loadModelWithoutController(String file) {
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(new File(file).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        T result;

        try {
            result = fxmlLoader.load();
        } catch (IOException | UnsupportedOperationException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}