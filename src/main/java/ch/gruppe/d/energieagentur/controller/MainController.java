package ch.gruppe.d.energieagentur.controller;

import ch.gruppe.d.energieagentur.assets.Assets;
import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.Date.Formatter;
import ch.gruppe.d.energieagentur.util.files.esl.ESLManager;
import ch.gruppe.d.energieagentur.util.files.FileManager;
import ch.gruppe.d.energieagentur.util.files.sdat.SDATManager;
import ch.gruppe.d.energieagentur.util.files.chooser.ChooserManager;
import ch.gruppe.d.energieagentur.util.ui.UIAlertMsg;
import ch.gruppe.d.energieagentur.util.ui.UIHelper;
import javafx.application.Platform;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static ch.gruppe.d.energieagentur.util.files.xml.model.adapter.Config.START_FROM_DATE;
import static ch.gruppe.d.energieagentur.util.files.xml.model.adapter.Config.START_TO_DATE;
import static ch.gruppe.d.energieagentur.util.ui.UIAlertMsg.MSG_NO_DATA_FOUND;

/**
 * This class is used as a Controller for the Main View
 */
public class MainController extends Controller {
    private static final CategoryAxis X_AXIS = new CategoryAxis();
    private static final NumberAxis Y_AXIS = new NumberAxis();
    private final XYChart.Series<String, Number> producedDataSeries = new XYChart.Series<>();
    private final XYChart.Series<String, Number> purchasedDataSeries = new XYChart.Series<>();
    public StackPane mainStackPane;
    public Rectangle selectRect;
    private int lastValue = -1;
    private LocalDate lastFromDate = LocalDate.now().plusDays(1);
    private LocalDate lastToDate = LocalDate.now().plusDays(1);
    public ComboBox<ValuesModel> valuesComboBox;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public Stage stage;
    public BorderPane mainBorderPane;

    /**
     * Initialization method of this class.
     * This Method gets executed after the View has been loaded.
     */
    @Override
    public void init() {
        try {
            valuesComboBox.setItems(VALUES_MODEL_OBSERVABLE_LIST);
            valuesComboBox.getSelectionModel().selectFirst();

            X_AXIS.setLabel("Datum");
            X_AXIS.setAnimated(false);

            producedDataSeries.setName("Einspeisung Netz");
            purchasedDataSeries.setName("Bezug Netz");

            fromDatePicker.setValue(START_FROM_DATE);
            toDatePicker.setValue(START_TO_DATE);

            valuesComboBox.valueProperty().addListener((observableValue, valuesModel, t1) -> {
                System.out.println(valuesComboBox.getSelectionModel().getSelectedItem().getName());
                try {
                    updateDiagram();
                } catch (URISyntaxException | JAXBException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            updateDiagram();
        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR);
        }
    }

    /**
     * Creates and shows the loading screen
     * @throws URISyntaxException
     * @throws FileNotFoundException
     */
    private void createLoadingScreen() throws URISyntaxException, FileNotFoundException {
        final ImageView loading = new ImageView();

        loading.setImage(new Image(new FileInputStream(new File(Assets.Loading.asUrl().toURI()))));

        mainBorderPane.setCenter(loading);
    }

    /**
     * prepares and fills data in
     * @param fileManager file manager to be able to get the data
     * @throws Exception
     */
    private void prepareAndFillData(FileManager fileManager) throws Exception {

        //checks the instance of the fileManager
        fileManager.readFolder(fileManager instanceof ESLManager ? getESLFolder() : getSdatFolder(), fromDatePicker.getValue(), toDatePicker.getValue());

        fillDataIntoSeries(fileManager instanceof ESLManager ? ESLManager.PRODUCED : SDATManager.PRODUCED, producedDataSeries);
        fillDataIntoSeries(fileManager instanceof ESLManager ? ESLManager.PURCHASED : SDATManager.PURCHASED, purchasedDataSeries);
    }

    /**
     * This method updates the diagramm with new data
     *
     * @throws URISyntaxException
     * @throws JAXBException
     * @throws FileNotFoundException
     */
    private void updateDiagram() throws URISyntaxException, JAXBException, FileNotFoundException {
        final int value = valuesComboBox.getSelectionModel().getSelectedItem().getValue();

        //has the old value been changed
        if (lastValue == value && lastFromDate.isEqual(fromDatePicker.getValue()) && lastToDate.isEqual(toDatePicker.getValue())) {
            return;
        }

        //creating objects needed for the diagram
        final LineChart<String, Number> DATA_CHART = new LineChart<>(X_AXIS, Y_AXIS);

        //config
        DATA_CHART.setCreateSymbols(false);

        createLoadingScreen();

        //filling diagram
        new Thread(() -> {
            try {
                switch (value) {
                    case 1 -> {
                        Y_AXIS.setLabel("kWh");
                        SDAT_MANAGER.setKwH(true);
                        prepareAndFillData(SDAT_MANAGER);
                    }
                    case 2 -> {
                        Y_AXIS.setLabel("kW");
                        SDAT_MANAGER.setKwH(false);
                        prepareAndFillData(SDAT_MANAGER);
                    }
                    default -> {
                        Y_AXIS.setLabel("kWh");
                        prepareAndFillData(ESL_MANAGER);
                    }
                }

                //checks if no data has been found
                if (producedDataSeries.getData().isEmpty() && purchasedDataSeries.getData().isEmpty()) {
                    Platform.runLater(() -> mainBorderPane.setCenter(new Text(MSG_NO_DATA_FOUND.msg)));
                } else {
                    DATA_CHART.getData().add(producedDataSeries);
                    DATA_CHART.getData().add(purchasedDataSeries);

                    Platform.runLater(() -> mainBorderPane.setCenter(DATA_CHART));
                }

            } catch (Exception e) {
                Platform.runLater(() -> UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR));
            }
        }).start();

        //setting the last values
        lastValue = value;
        lastFromDate = fromDatePicker.getValue();
        lastToDate = toDatePicker.getValue();
    }

    /**
     * Checks if the user already has chosen an SDAT folder to be able to choose between the chosen one or the standard one
     *
     * @return the correct SDAT Folder
     * @throws URISyntaxException
     */
    private File getSdatFolder() throws URISyntaxException {
        if (sdatFolder == null) {
            return getStandardSDATFolder();
        }
        return sdatFolder;
    }

    /**
     * Checks if the user already has chosen an ESL folder to be able to choose between the chosen one or the standard one
     *
     * @return the correct ESL Folder
     * @throws URISyntaxException
     */
    private File getESLFolder() throws URISyntaxException {
        if (eslFolder == null) {
            return getStandardESLFolder();
        }
        return eslFolder;
    }

    /**
     * This method fills the given map into the given series
     *
     * @param data   given map
     * @param series given series
     */
    private void fillDataIntoSeries(Map<LocalDateTime, BigDecimal> data, XYChart.Series<String, Number> series) {
        series.getData().clear();

        for (Map.Entry<LocalDateTime, BigDecimal> dataEntry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(
                    Formatter.formatDateTime(dataEntry.getKey()),
                    dataEntry.getValue()
            ));
        }
    }

    /**
     * This method gets the standard ESL folder
     *
     * @return standard ESL Folder
     * @throws URISyntaxException
     */
    private File getStandardESLFolder() throws URISyntaxException {
        return new File(Objects.requireNonNull(MainController.class.getClassLoader().getResource("ESL-Files")).toURI());
    }

    /**
     * This method gets the standard SDAT folder
     *
     * @return standard SDAT Folder
     * @throws URISyntaxException
     */
    private File getStandardSDATFolder() throws URISyntaxException {
        return new File(Objects.requireNonNull(MainController.class.getClassLoader().getResource("SDAT-Files")).toURI());
    }

    /**
     * This Method opens the Choose Dialog for choosing a SDAT-Folder
     */
    public void selectSDATFolder() {
        sdatFolder = ChooserManager.getChoosedOpenFolder(stage);
        try {
            updateDiagram();
        } catch (URISyntaxException | JAXBException | FileNotFoundException e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR_READING_SDAT);
        }
    }

    /**
     * This Method opens the Choose Dialog for choosing an ESL-Folder
     */
    public void selectESLFolder() {
        eslFolder = ChooserManager.getChoosedOpenFolder(stage);
        try {
            updateDiagram();
        } catch (URISyntaxException | JAXBException | FileNotFoundException e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR_READING_ESL);
        }
    }

    /**
     * This Method sets the From-Date to the previous month
     */
    public void fromMonthBackward() {
        fromDatePicker.setValue(fromDatePicker.getValue().minusMonths(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the From-Date to the previous day
     */
    public void fromDayBackward() {
        fromDatePicker.setValue(fromDatePicker.getValue().minusDays(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the From-Date to the next day
     */
    public void fromDayForward() {
        fromDatePicker.setValue(fromDatePicker.getValue().plusDays(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the From-Date to the next month
     */
    public void fromMonthForward() {
        fromDatePicker.setValue(fromDatePicker.getValue().plusMonths(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the To-Date to the previous month
     */
    public void toMonthBackward() {
        toDatePicker.setValue(toDatePicker.getValue().minusMonths(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the To-Date to the previous day
     */
    public void toDayBackward() {
        toDatePicker.setValue(toDatePicker.getValue().minusDays(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the To-Date to the next day
     */
    public void toDayForward() {
        toDatePicker.setValue(toDatePicker.getValue().plusDays(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the To-Date to the next month
     */
    public void toMonthForward() {
        toDatePicker.setValue(toDatePicker.getValue().plusMonths(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the Selected Date to the next day
     */
    public void dayForward() {
        fromDayForward();
        toDayForward();

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the Selected Date to the previous day
     */
    public void dayBackward() {
        fromDayBackward();
        toDayBackward();

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the Selected Date to the next month
     */
    public void monthForward() {
        fromMonthForward();
        toMonthForward();

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the Selected Date to the previous month
     */
    public void monthBackward() {
        fromMonthBackward();
        toMonthBackward();

        updateDiagramAndHandleExceptions();
    }

    /**
     * This method calls the updateDiagram(); method and catches the exception
     */
    private void updateDiagramAndHandleExceptions() {
        try {
            updateDiagram();
        } catch (URISyntaxException | JAXBException | FileNotFoundException e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR);
        }
    }

    /**
     * This method gets triggered when the date gets changed from the datepicker
     * It updates the diagram with the new from date
     */
    public void changeFromDate() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        if (fromDate.isAfter(toDate)) {
            toDatePicker.setValue(fromDate.plusDays(1));
        }

        updateDiagramAndHandleExceptions();
    }

    /**
     * This method gets triggered when the date gets changed from the datepicker
     * It updates the diagram with the new to date
     */
    public void changeToDate() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        if (toDate.isBefore(fromDate)) {
            fromDatePicker.setValue(toDate.minusDays(1));
        }

        updateDiagramAndHandleExceptions();
    }
}
