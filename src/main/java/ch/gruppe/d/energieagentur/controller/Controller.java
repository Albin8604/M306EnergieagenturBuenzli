package ch.gruppe.d.energieagentur.controller;


import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.files.esl.ESLManager;
import ch.gruppe.d.energieagentur.util.files.export.CSVFileManager;
import ch.gruppe.d.energieagentur.util.files.export.JSONFileManager;
import ch.gruppe.d.energieagentur.util.files.export.model.CSVExport;
import ch.gruppe.d.energieagentur.util.files.export.model.JSONExport;
import ch.gruppe.d.energieagentur.util.files.sdat.SDATManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

/**
 * This Class is used as the Parent controller
 * Mainly to be able to use same managers and stuff in multiple controllers
 */
public abstract class Controller {
    //Values of the Combo Box
    protected static final ObservableList<ValuesModel> VALUES_MODEL_OBSERVABLE_LIST = FXCollections.observableArrayList(
            new ValuesModel("Relative Wert [kWh]", 1),
            new ValuesModel("Relative Wert [kW]", 2),
            new ValuesModel("Zählerstände", 3)
    );

    //file managers
    protected static final SDATManager SDAT_MANAGER = new SDATManager();
    protected static final ESLManager ESL_MANAGER = new ESLManager();

    //export file managers
    protected static final JSONFileManager<JSONExport> JSON_FILE_MANAGER = new JSONFileManager<>(JSONExport.class);
    protected static final CSVFileManager<CSVExport> CSV_FILE_MANAGER = new CSVFileManager<>(CSVExport.class);

    //selected folders
    public static File eslFolder = null;
    public static File sdatFolder = null;

    public abstract void init();
}