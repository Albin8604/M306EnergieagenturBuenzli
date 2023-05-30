package ch.gruppe.d.energieagentur.controller;

import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.files.chooser.ChooserManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * This class is used as a Controller for the Main View
 */
public class MainController extends Controller {
    private int lastValue = 1;
    public ComboBox<ValuesModel> valuesComboBox;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public LineChart<LocalDateTime, Number> dataChart;
    public Stage stage;
    public BorderPane mainBorderPane;

    private LocalDateTime selectedTime = LocalDateTime.now();

    /**
     * Initialization method of this class.
     * This Method gets executed after the View has been loaded.
     */
    @Override
    public void init() {
        valuesComboBox.setItems(VALUES_MODEL_OBSERVABLE_LIST);
        valuesComboBox.getSelectionModel().selectFirst();

        valuesComboBox.valueProperty().addListener((observableValue, valuesModel, t1) -> updateDiagram());
    }

    /**
     * This Method updates the diagram or changes it completely
     */
    private void updateDiagram() {
        final int value = valuesComboBox.getSelectionModel().getSelectedItem().getValue();

        if (lastValue == value){
            return;
        }

        switch (value){
            case 1:
                break;
            case 2:
                break;
            default:


                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();

                xAxis.setLabel("Datum");
                yAxis.setLabel("kWh");


                break;
        }

        lastValue = value;
    }

    /**
     * This Method opens the Choose Dialog for choosing a SDAT-Folder
     */
    public void selectSDATFolder() {
        File folder = ChooserManager.getChoosedOpenFolder(stage);
        System.out.println(folder.getAbsolutePath());
    }

    /**
     * This Method opens the Choose Dialog for choosing an ESL-Folder
     */
    public void selectESLFolder() {
        File folder = ChooserManager.getChoosedOpenFolder(stage);
        System.out.println(folder.getAbsolutePath());
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
        selectedTime = selectedTime.plusDays(1);
    }

    /**
     * This Method sets the Selected Date to the previous day
     */
    public void dayBackward() {
        selectedTime = selectedTime.minusDays(1);
    }

    /**
     * This Method sets the Selected Date to the next month
     */
    public void monthForward() {
        selectedTime = selectedTime.plusMonths(1);
    }

    /**
     * This Method sets the Selected Date to the previous month
     */
    public void monthBackward() {
        selectedTime = selectedTime.minusMonths(1);
    }
}
