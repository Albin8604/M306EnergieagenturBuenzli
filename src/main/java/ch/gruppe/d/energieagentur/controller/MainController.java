package ch.gruppe.d.energieagentur.controller;

import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.Date.Formatter;
import ch.gruppe.d.energieagentur.util.ESLManager;
import ch.gruppe.d.energieagentur.util.SDATManager;
import ch.gruppe.d.energieagentur.util.files.chooser.ChooserManager;
import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ESL;
import ch.gruppe.d.energieagentur.util.ui.UIAlertMsg;
import ch.gruppe.d.energieagentur.util.ui.UIHelper;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.gillius.jfxutils.chart.ChartZoomManager;
import org.gillius.jfxutils.chart.StableTicksAxis;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

    //todo zoom https://stackoverflow.com/questions/22099650/zoom-bar-chart-with-mouse-wheel
    private static final LineChart<String, Number> DATA_CHART =
            new LineChart<>(X_AXIS, Y_AXIS);
    public StackPane mainStackPane;
    public Rectangle selectRect;
    private File eslFolder = null;
    private File sdatFolder = null;
    private int lastValue = 1;
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

            DATA_CHART.setCreateSymbols(false);

            X_AXIS.setLabel("Datum");
            X_AXIS.setAnimated(false);

            Y_AXIS.setLabel("kWh");

            fromDatePicker.setValue(START_FROM_DATE);
            toDatePicker.setValue(START_TO_DATE);

            valuesComboBox.valueProperty().addListener((observableValue, valuesModel, t1) -> {
                System.out.println(valuesComboBox.getSelectionModel().getSelectedItem().getName());
                try {
                    updateDiagram();
                } catch (URISyntaxException | JAXBException e) {
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
     * This Method updates the diagram or changes it completely
     */
    private void updateDiagram() throws URISyntaxException, JAXBException {
        final int value = valuesComboBox.getSelectionModel().getSelectedItem().getValue();
        final XYChart.Series producedDataSeries = new XYChart.Series();
        final XYChart.Series purchasedDataSeries = new XYChart.Series();

        producedDataSeries.setName("Einspeisung Netz");
        purchasedDataSeries.setName("Bezug Netz");

        if (lastValue == value) {
            return;
        }

        mainStackPane.getChildren().remove(DATA_CHART);
        DATA_CHART.getData().clear();

        switch (value) {
            case 1:
                if (sdatFolder == null){
                    SDAT_MANAGER.readFolder(getStandardSDATFolder(),fromDatePicker.getValue(),toDatePicker.getValue());
                }else {
                    SDAT_MANAGER.readFolder(sdatFolder,fromDatePicker.getValue(),toDatePicker.getValue());
                }

                fillDataIntoSeries(SDATManager.PRODUCED, producedDataSeries);
                fillDataIntoSeries(SDATManager.PURCHASED, purchasedDataSeries);

                break;
            case 2:
                break;
            default:
                if (eslFolder == null) {
                    ESL_MANAGER.readFolder(getStandardESLFolder(),fromDatePicker.getValue(),toDatePicker.getValue());
                }else {
                    ESL_MANAGER.readFolder(eslFolder,fromDatePicker.getValue(),toDatePicker.getValue());
                }

                fillDataIntoSeries(ESLManager.PRODUCED, producedDataSeries);
                fillDataIntoSeries(ESLManager.PURCHASED, purchasedDataSeries);

                break;
        }

        DATA_CHART.getData().add(producedDataSeries);
        DATA_CHART.getData().add(purchasedDataSeries);

        mainStackPane.getChildren().add(DATA_CHART);

        lastValue = value;
    }

    private void fillDataIntoSeries(Map<LocalDateTime, BigDecimal> data, XYChart.Series series) {
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
        } catch (URISyntaxException | JAXBException e) {
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
        } catch (URISyntaxException | JAXBException e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR_READING_ESL);
        }
    }

    /**
     * This Method sets the From-Date to the previous month
     */
    public void fromMonthBackward() {
        fromDatePicker.setValue(fromDatePicker.getValue().minusMonths(1));
    }

    /**
     * This Method sets the From-Date to the previous day
     */
    public void fromDayBackward() {
        fromDatePicker.setValue(fromDatePicker.getValue().minusDays(1));
    }

    /**
     * This Method sets the From-Date to the next day
     */
    public void fromDayForward() {
        fromDatePicker.setValue(fromDatePicker.getValue().plusDays(1));
    }

    /**
     * This Method sets the From-Date to the next month
     */
    public void fromMonthForward() {
        fromDatePicker.setValue(fromDatePicker.getValue().plusMonths(1));
    }

    /**
     * This Method sets the To-Date to the previous month
     */
    public void toMonthBackward() {
        toDatePicker.setValue(toDatePicker.getValue().minusMonths(1));
    }

    /**
     * This Method sets the To-Date to the previous day
     */
    public void toDayBackward() {
        toDatePicker.setValue(toDatePicker.getValue().minusDays(1));
    }

    /**
     * This Method sets the To-Date to the next day
     */
    public void toDayForward() {
        toDatePicker.setValue(toDatePicker.getValue().plusDays(1));
    }

    /**
     * This Method sets the To-Date to the next month
     */
    public void toMonthForward() {
        toDatePicker.setValue(toDatePicker.getValue().plusMonths(1));
    }

    /**
     * This Method sets the Selected Date to the next day
     */
    public void dayForward() {
        fromDayForward();
        toDayForward();
    }

    /**
     * This Method sets the Selected Date to the previous day
     */
    public void dayBackward() {
        fromDayBackward();
        toDayBackward();
    }

    /**
     * This Method sets the Selected Date to the next month
     */
    public void monthForward() {
        fromMonthForward();
        toMonthForward();
    }

    /**
     * This Method sets the Selected Date to the previous month
     */
    public void monthBackward() {
        fromMonthBackward();
        toMonthBackward();
    }
}
