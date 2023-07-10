package ch.gruppe.d.energieagentur.controller;

import ch.gruppe.d.energieagentur.component.LineChartComponent;
import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.files.FileManager;
import ch.gruppe.d.energieagentur.util.files.chooser.ChooserManager;
import ch.gruppe.d.energieagentur.util.files.chooser.Extensions;
import ch.gruppe.d.energieagentur.util.files.esl.ESLManager;
import ch.gruppe.d.energieagentur.util.files.export.model.CSVExport;
import ch.gruppe.d.energieagentur.util.files.export.model.JSONExport;
import ch.gruppe.d.energieagentur.util.files.export.model.SensorId;
import ch.gruppe.d.energieagentur.util.files.export.model.Zaehlerstand;
import ch.gruppe.d.energieagentur.util.files.sdat.SDATManager;
import ch.gruppe.d.energieagentur.util.ui.UIAlertMsg;
import ch.gruppe.d.energieagentur.util.ui.UIHelper;
import javafx.embed.swing.SwingNode;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static ch.gruppe.d.energieagentur.Config.START_FROM_DATE;
import static ch.gruppe.d.energieagentur.Config.START_TO_DATE;

/**
 * This class is used as a Controller for the Main View
 */
public class MainController extends Controller {
    //fxml objects
    public StackPane mainStackPane;
    public Rectangle selectRect;
    public Button jsonExportBtn;
    public Button csvExportBtn;
    public ComboBox<ValuesModel> valuesComboBox;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public Stage stage;
    public BorderPane mainBorderPane;

    //last values
    private int lastValue = -1;
    private LocalDate lastFromDate = LocalDate.now().plusDays(1);
    private LocalDate lastToDate = LocalDate.now().plusDays(1);
    private boolean diagramUpdated = false;

    /**
     * Initialization method of this class.
     * This Method gets executed after the View has been loaded.
     */
    @Override
    public void init() {
        try {
            valuesComboBox.setItems(VALUES_MODEL_OBSERVABLE_LIST);
            valuesComboBox.getSelectionModel().selectFirst();

            fromDatePicker.setValue(START_FROM_DATE);
            toDatePicker.setValue(START_TO_DATE);

            valuesComboBox.valueProperty().addListener((observableValue, valuesModel, t1) -> {
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
     * prepares and fills data in
     *
     * @param fileManager file manager to be able to get the data
     * @throws Exception
     */
    private void prepareAndFillData(FileManager fileManager, SwingNode swingNode, boolean isKwH) throws Exception {

        //checks the instance of the fileManager
        fileManager.readFolder(fileManager instanceof ESLManager ? getESLFolder() : getSdatFolder(),
                fromDatePicker.getValue(), toDatePicker.getValue(),
                false);

        //sets the chart
        swingNode.setContent(new LineChartComponent(
                fileManager instanceof ESLManager ? ESLManager.produced : SDATManager.produced,
                fileManager instanceof ESLManager ? ESLManager.purchased : SDATManager.purchased,
                isKwH
        ));
    }

    /**
     * This method updates the diagram with new data
     *
     * @throws URISyntaxException
     * @throws JAXBException
     * @throws FileNotFoundException
     */
    private void updateDiagram() throws URISyntaxException, JAXBException, FileNotFoundException {

        //to disable multiple method calls in short time
        if (diagramUpdated) {
            return;
        }

        diagramUpdated = true;

        new Thread(() -> {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            diagramUpdated = false;
        }).start();


        final int value = valuesComboBox.getSelectionModel().getSelectedItem().getValue();

        //has the old value been changed
        if (lastValue == value && lastFromDate.isEqual(fromDatePicker.getValue()) && lastToDate.isEqual(toDatePicker.getValue())) {
            return;
        }

        //creating objects needed for the diagram
        final SwingNode SWING_CHART_NODE = new SwingNode();

        //filling diagram
        try {
            switch (value) {
                case 1 -> {
                    SDAT_MANAGER.setKwH(true);

                    //to prevent corrupt folder reading
                    try {
                        prepareAndFillData(SDAT_MANAGER, SWING_CHART_NODE, SDAT_MANAGER.isKwH());
                    } catch (IllegalArgumentException e) {
                        sdatFolder = getStandardSDATFolder();
                        updateDiagram();
                        throw new IllegalArgumentException(e);
                    }
                }
                case 2 -> {
                    SDAT_MANAGER.setKwH(false);

                    //to prevent corrupt folder reading
                    try {
                        prepareAndFillData(SDAT_MANAGER, SWING_CHART_NODE, SDAT_MANAGER.isKwH());
                    } catch (IllegalArgumentException e) {
                        sdatFolder = getStandardSDATFolder();
                        updateDiagram();
                        throw new IllegalArgumentException(e);
                    }
                }
                default -> {

                    //to prevent corrupt folder reading
                    try {
                        prepareAndFillData(ESL_MANAGER, SWING_CHART_NODE, true);
                    } catch (IllegalArgumentException e) {
                        eslFolder = getStandardESLFolder();
                        updateDiagram();
                        throw new IllegalArgumentException(e);
                    }
                }
            }

            mainBorderPane.setCenter(SWING_CHART_NODE);

        } catch (Exception e) {
            e.printStackTrace();
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR);
        }


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

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method opens the Choose Dialog for choosing an ESL-Folder
     */
    public void selectESLFolder() {
        eslFolder = ChooserManager.getChoosedOpenFolder(stage);

        updateDiagramAndHandleExceptions();
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
        fromDatePicker.setValue(fromDatePicker.getValue().plusDays(1));
        toDatePicker.setValue(toDatePicker.getValue().plusDays(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the Selected Date to the previous day
     */
    public void dayBackward() {
        fromDatePicker.setValue(fromDatePicker.getValue().minusDays(1));
        toDatePicker.setValue(toDatePicker.getValue().minusDays(1));

        updateDiagramAndHandleExceptions();
    }

    /**
     * This Method sets the Selected Date to the next month
     */
    public void monthForward() {
        //resolving bug this way
        fromDatePicker.setValue(fromDatePicker.getValue().plusMonths(2));
        toDatePicker.setValue(toDatePicker.getValue().plusMonths(2));

        monthBackward();
    }

    /**
     * This Method sets the Selected Date to the previous month
     */
    public void monthBackward() {
        fromDatePicker.setValue(fromDatePicker.getValue().minusMonths(1));
        toDatePicker.setValue(toDatePicker.getValue().minusMonths(1));

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

    /**
     * Gets called within the click on the JSON Export button
     * Exports all the data in JSON Format
     */
    public void jsonExport() {
        if (!readAllESLData()) {
            return;
        }

        //gets the file to save the json data
        final File chosenJsonFileLocation = ChooserManager.getChoosedSaveFile(stage, Extensions.JSON);

        //creating the list for the export objects
        final List<JSONExport> jsonExportData = new ArrayList<>();

        //creating the Zaehlerstand arrays to be able to store each value
        final Zaehlerstand[] producedZaehlerstandArray = new Zaehlerstand[ESLManager.produced.size()];
        final Zaehlerstand[] purchasedZaehlerstandArray = new Zaehlerstand[ESLManager.purchased.size()];

        //counter for the arrays
        int producedCounter = 0;
        int purchasedCounter = 0;

        //filling the data
        for (Map.Entry<LocalDateTime, BigDecimal> producedESLEntrySet : ESLManager.produced.entrySet()) {
            producedZaehlerstandArray[producedCounter++] = new Zaehlerstand(producedESLEntrySet.getKey(), producedESLEntrySet.getValue().doubleValue());
        }

        for (Map.Entry<LocalDateTime, BigDecimal> purchasedESLEntrySet : ESLManager.purchased.entrySet()) {
            purchasedZaehlerstandArray[purchasedCounter++] = new Zaehlerstand(purchasedESLEntrySet.getKey(), purchasedESLEntrySet.getValue().doubleValue());
        }

        jsonExportData.add(new JSONExport(SensorId.ID735, producedZaehlerstandArray));
        jsonExportData.add(new JSONExport(SensorId.ID742, purchasedZaehlerstandArray));

        //writing json
        JSON_FILE_MANAGER.write(jsonExportData, chosenJsonFileLocation.getAbsolutePath());
    }

    /**
     * Gets called within the click on the CSV Export button
     * Exports all the data in CSV Format
     * Creates two files for each relevant sensor ID
     */
    public void csvExport() {
        if (!readAllESLData()) {
            return;
        }

        //get folder to save the csv export
        final File chosenCSVExportFolder = ChooserManager.getChoosedOpenFolder(stage);

        //creating the lists with the export objects
        final List<CSVExport> producedCSVExportList = new ArrayList<>();
        final List<CSVExport> purchasedCSVExportList = new ArrayList<>();

        //filling the lists
        for (Map.Entry<LocalDateTime, BigDecimal> producedESLEntrySet : ESLManager.produced.entrySet()) {
            producedCSVExportList.add(new CSVExport(producedESLEntrySet.getKey(), producedESLEntrySet.getValue()));
        }

        for (Map.Entry<LocalDateTime, BigDecimal> purchasedESLEntrySet : ESLManager.purchased.entrySet()) {
            purchasedCSVExportList.add(new CSVExport(purchasedESLEntrySet.getKey(), purchasedESLEntrySet.getValue()));
        }

        //writing the files
        CSV_FILE_MANAGER.write(producedCSVExportList, chosenCSVExportFolder.getAbsolutePath() + "/" + SensorId.ID735);
        CSV_FILE_MANAGER.write(purchasedCSVExportList, chosenCSVExportFolder.getAbsolutePath() + "/" + SensorId.ID742);
    }

    /**
     * readsAllESLData for the export
     *
     * @return if the operation was successful
     */
    private boolean readAllESLData() {
        try {
            ESL_MANAGER.readFolder(getESLFolder(), null, null, true);
        } catch (JAXBException | URISyntaxException e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.ERROR_READING_ESL);
            return false;
        }

        return true;
    }
}
