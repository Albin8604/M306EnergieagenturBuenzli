package ch.gruppe.d.energieagentur.controller;

import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.Date.Formatter;
import ch.gruppe.d.energieagentur.util.files.chooser.ChooserManager;
import ch.gruppe.d.energieagentur.util.files.xml.model.esl.ESL;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * This class is used as a Controller for the Main View
 */
public class MainController extends Controller {
    private static final CategoryAxis X_AXIS = new CategoryAxis();
    private static final NumberAxis Y_AXIS = new NumberAxis();
    private static final LineChart<String, Number> NEW_CHART =
            new LineChart<>(X_AXIS, Y_AXIS);
    private File eslFolder = null;
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

        X_AXIS.setLabel("Datum");
        Y_AXIS.setLabel("kWh");

        valuesComboBox.valueProperty().addListener((observableValue, valuesModel, t1) -> {
            System.out.println(valuesComboBox.getSelectionModel().getSelectedItem().getName());
            updateDiagram();
        });
        updateDiagram();
    }

    /**
     * This Method updates the diagram or changes it completely
     */
    private void updateDiagram() {
        final int value = valuesComboBox.getSelectionModel().getSelectedItem().getValue();

        if (lastValue == value) {
            return;
        }

        final XYChart.Series series = new XYChart.Series();

        switch (value) {
            case 1:
                break;
            case 2:
                break;
            default:
                series.setName("Zählerstände");

                final List<ESL> eslList = getAllESLData(null);
                final Map<LocalDateTime, BigDecimal> diagramData = new TreeMap<>();

                eslList.forEach(esl -> {
                    esl.getMeter().getTimePeriodList().forEach(timePeriod -> {
                        timePeriod.getValueRowList().forEach(valueRow -> {
                            diagramData.put(valueRow.getValueTimeStamp() == null ?
                                            timePeriod.getEnd() :
                                            valueRow.getValueTimeStamp(),
                                    valueRow.getValue());
                        });
                    });
                });

                for (Map.Entry<LocalDateTime, BigDecimal> localDateTimeBigDecimalEntry : diagramData.entrySet()) {
                    series.getData().add(new XYChart.Data(
                            Formatter.formatDateTime(localDateTimeBigDecimalEntry.getKey()),
                            localDateTimeBigDecimalEntry.getValue()
                    ));
                }

                break;
        }

        NEW_CHART.getData().clear();
        NEW_CHART.getData().add(series);

        lastValue = value;
    }

    /**
     * Converts all ESL Files into ESL model classes
     *
     * @param folder folder of the ESL files
     * @return all ESL model classes as list
     */
    private List<ESL> getAllESLData(File folder) {
        if (folder == null) {
            try {
                return getAllESLData(new File(Objects.requireNonNull(MainController.class.getClassLoader().getResource("ESL-Files")).toURI()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(ESL.class);
            Unmarshaller mar = context.createUnmarshaller();

            return Arrays.stream(folder.listFiles()).map(file -> {
                try {
                    return (ESL) mar.unmarshal(file);
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
            }).toList();


        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
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
        eslFolder = ChooserManager.getChoosedOpenFolder(stage);
        updateDiagram();
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
