package ch.gruppe.d.energieagentur.controller;

import ch.gruppe.d.energieagentur.assets.Assets;
import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.Date.Formatter;
import ch.gruppe.d.energieagentur.util.files.ESLManager;
import ch.gruppe.d.energieagentur.util.files.SDATManager;
import ch.gruppe.d.energieagentur.util.files.chooser.ChooserManager;
import ch.gruppe.d.energieagentur.util.ui.UIAlertMsg;
import ch.gruppe.d.energieagentur.util.ui.UIHelper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

/**
 * This class is used as a Controller for the Main View
 */
public class MainController extends Controller {
    /*
        private static final StableTicksAxis X_AXIS = new StableTicksAxis();
        private static final StableTicksAxis Y_AXIS = new StableTicksAxis();

         */
    private static final CategoryAxis X_AXIS = new CategoryAxis();
    private static final NumberAxis Y_AXIS = new NumberAxis();
    public static final String MSG_NO_DATA_FOUND = "No Data found in the given Timespan";

    //todo zoom https://stackoverflow.com/questions/22099650/zoom-bar-chart-with-mouse-wheel
    //private static LineChart<String, Number> DATA_CHART;
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

    private void createLoadingScreen() throws URISyntaxException, FileNotFoundException {
        final ImageView loading = new ImageView();

        loading.setImage(new Image(new FileInputStream(new File(Assets.Loading.asUrl().toURI()))));

        mainBorderPane.setCenter(loading);
    }

    private void prepareAndFillSdatData(XYChart.Series<String, Number> producedDataSeries, XYChart.Series<String, Number> purchasedDataSeries) {
        if (sdatFolder == null) {
            try {
                SDAT_MANAGER.readFolder(getStandardSDATFolder(), fromDatePicker.getValue(), toDatePicker.getValue());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            SDAT_MANAGER.readFolder(sdatFolder, fromDatePicker.getValue(), toDatePicker.getValue());
        }
        fillDataIntoSeries(SDATManager.PRODUCED, producedDataSeries);
        fillDataIntoSeries(SDATManager.PURCHASED, purchasedDataSeries);
    }

    /**
     * This Method updates the diagram or changes it completely
     */
    private void updateDiagram() throws URISyntaxException, JAXBException, FileNotFoundException {
        final int value = valuesComboBox.getSelectionModel().getSelectedItem().getValue();

        if (lastValue == value && lastFromDate.isEqual(fromDatePicker.getValue()) && lastToDate.isEqual(toDatePicker.getValue())) {
            return;
        }

        final XYChart.Series<String, Number> producedDataSeries = new XYChart.Series<>();
        final XYChart.Series<String, Number> purchasedDataSeries = new XYChart.Series<>();
        final LineChart<String, Number> DATA_CHART = new LineChart<>(X_AXIS, Y_AXIS);


        producedDataSeries.setName("Einspeisung Netz");
        purchasedDataSeries.setName("Bezug Netz");

        DATA_CHART.setCreateSymbols(false);

        createLoadingScreen();

        new Thread(() -> {
            switch (value) {
                case 1 -> {
                    Y_AXIS.setLabel("kWh");
                    SDAT_MANAGER.setKwH(true);
                    prepareAndFillSdatData(producedDataSeries, purchasedDataSeries);
                }
                case 2 -> {
                    Y_AXIS.setLabel("kW");
                    SDAT_MANAGER.setKwH(false);
                    prepareAndFillSdatData(producedDataSeries, purchasedDataSeries);
                }
                default -> {
                    Y_AXIS.setLabel("kWh");
                    if (eslFolder == null) {
                        try {
                            ESL_MANAGER.readFolder(getStandardESLFolder(), fromDatePicker.getValue(), toDatePicker.getValue());
                        } catch (JAXBException | URISyntaxException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            ESL_MANAGER.readFolder(eslFolder, fromDatePicker.getValue(), toDatePicker.getValue());
                        } catch (JAXBException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    fillDataIntoSeries(ESLManager.PRODUCED, producedDataSeries);
                    fillDataIntoSeries(ESLManager.PURCHASED, purchasedDataSeries);
                }
            }

            if (producedDataSeries.getData().isEmpty() && purchasedDataSeries.getData().isEmpty()){
                Platform.runLater(() -> mainBorderPane.setCenter(new Text(MSG_NO_DATA_FOUND)));
            }else {
                DATA_CHART.getData().add(producedDataSeries);
                DATA_CHART.getData().add(purchasedDataSeries);

                Platform.runLater(() -> mainBorderPane.setCenter(DATA_CHART));
            }
        }).start();

        lastValue = value;
        lastFromDate = fromDatePicker.getValue();
        lastToDate = toDatePicker.getValue();
    }

    private void fillDataIntoSeries(Map<LocalDateTime, BigDecimal> data, XYChart.Series<String, Number> series) {
        for (Map.Entry<LocalDateTime, BigDecimal> dataEntry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(
                    Formatter.formatDateTime(dataEntry.getKey()),
                    dataEntry.getValue()
            ));
        }
    }

    private File getStandardESLFolder() throws URISyntaxException {
        return new File(Objects.requireNonNull(MainController.class.getClassLoader().getResource("ESL-Files")).toURI());
    }

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

    private void updateDiagramAndHandleExceptions() {
        try {
            updateDiagram();
        } catch (URISyntaxException | JAXBException | FileNotFoundException e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR);
        }
    }

    public void changeFromDate() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        if (fromDate.isAfter(toDate)){
            toDatePicker.setValue(fromDate.plusDays(1));
        }

        updateDiagramAndHandleExceptions();
    }

    public void changeToDate() {
        LocalDate fromDate = fromDatePicker.getValue();
        LocalDate toDate = toDatePicker.getValue();
        if (toDate.isBefore(fromDate)){
            fromDatePicker.setValue(toDate.minusDays(1));
        }

        updateDiagramAndHandleExceptions();
    }
}
