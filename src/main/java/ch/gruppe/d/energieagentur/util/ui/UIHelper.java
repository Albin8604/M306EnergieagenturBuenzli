package ch.gruppe.d.energieagentur.util.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;

/**
 * This class is a helper class for the UI
 */
public class UIHelper {

    /**
     * This method creates a spinner
     *
     * @param alertType   AlertType
     * @param uiAlertMsg   UIAlertMsg
     * @return Spinner<Integer>
     */
    public static void createAndShowAlert(Alert.AlertType alertType, UIAlertMsg uiAlertMsg) {
        createAndShowAlert(alertType, uiAlertMsg.msg);
    }

    public static void createAndShowAlert(Alert.AlertType alertType, String uiAlertMsg) {
        createAndShowAlert(alertType, uiAlertMsg, null);
    }

    /**
     * This method creates and shows an alert
     *
     * @param alertType   Alert.AlertType
     * @param uiAlertMsg  String
     * @param graphic     ImageView
     */
    public static void createAndShowAlert(Alert.AlertType alertType, String uiAlertMsg, ImageView graphic) {

        String title;
        String header;

        if (alertType == Alert.AlertType.ERROR) {
            title = "ERROR :/";
            header = "An error occurred";
        } else if (alertType == Alert.AlertType.WARNING) {
            title = "WARNING";
            header = "A warning occurred";
        } else if (alertType == Alert.AlertType.INFORMATION) {
            title = "Information";
            header = "We have an information for you";
        } else if (alertType == Alert.AlertType.CONFIRMATION) {
            title = "Confirmation";
            header = "Here's a confirmation for you";
        } else {
            throw new RuntimeException();
        }

        alert(alertType, graphic, title, header, uiAlertMsg);
    }

    /**
     * This method creates and shows an alert
     *
     * @param feedbackType Alert.AlertType
     * @param graphic      ImageView
     * @param title        String
     * @param headerText   String
     * @param bodyText     String
     */
    private static void alert(Alert.AlertType feedbackType, ImageView graphic, String title, String headerText, String bodyText) {
        Alert alert = new Alert(feedbackType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);

        if (graphic != null) {
            alert.setGraphic(graphic);
        }

        alert.showAndWait();
    }

    /**
     * This method creates a spinner
     *
     * @param min      int
     * @param max      int
     * @param startWith int
     * @return Spinner<Integer>
     */
    public static void initIntegerSpinner(Spinner<Integer> spinner, int min, int max, int startWith) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, startWith);
        spinner.setValueFactory(spinnerValueFactory);
    }

    /**
     * This method creates a spinner
     *
     * @param min      double
     * @param max      double
     * @param startWith double
     * @return Spinner<Double>
     */
    public static void initDoubleSpinner(Spinner<Double> spinner, double min, double max, double startWith) {
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, startWith);
        spinner.setValueFactory(spinnerValueFactory);
    }
}
