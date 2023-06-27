package ch.gruppe.d.energieagentur.util.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;

public class UIHelper {
    public static void createAndShowAlert(Alert.AlertType alertType, UIAlertMsg uiAlertMsg) {
        createAndShowAlert(alertType, uiAlertMsg.msg);
    }

    public static void createAndShowAlert(Alert.AlertType alertType, String uiAlertMsg) {
        createAndShowAlert(alertType, uiAlertMsg, null);
    }

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

    public static void initIntegerSpinner(Spinner<Integer> spinner, int min, int max, int startWith) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, startWith);
        spinner.setValueFactory(spinnerValueFactory);
    }

    public static void initDoubleSpinner(Spinner<Double> spinner, double min, double max, double startWith) {
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, startWith);
        spinner.setValueFactory(spinnerValueFactory);
    }
}
