package ch.gruppe.d.energieagentur.util.ui;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;

/**
 * This class is used as a helper for the JavaFx UI
 */
public class UIHelper {
    /**
     * This method creates and shows an alert
     *
     * @param alertType  given alert type
     * @param uiAlertMsg given alert message
     */
    public static void createAndShowAlert(Alert.AlertType alertType, UIAlertMsg uiAlertMsg) {
        createAndShowAlert(alertType, uiAlertMsg.msg);
    }

    /**
     * This method creates and shows an alert with a custom message
     *
     * @param alertType  given alert type
     * @param uiAlertMsg given alert message
     */
    public static void createAndShowAlert(Alert.AlertType alertType, String uiAlertMsg) {
        createAndShowAlert(alertType, uiAlertMsg, null);
    }

    /**
     * This method creates and shows an alert with a custom message and custom graphic
     *
     * @param alertType  given alert type
     * @param uiAlertMsg given alert message
     * @param graphic    given graphic
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
     * This method creates and shows an alert with a custom message, custom graphic, custom title, custom headerText and custom bodyText
     *
     * @param feedbackType given alert type
     * @param graphic      given graphic
     * @param title        given title
     * @param headerText   given header text
     * @param bodyText     given body text
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
}