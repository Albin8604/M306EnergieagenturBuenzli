package ch.gruppe.d.energieagentur.controller;


import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.files.esl.ESLManager;
import ch.gruppe.d.energieagentur.util.files.sdat.SDATManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

/**
 * This class is used as a Controller for the MainController
 */
public abstract class Controller {

    /**
     * This ObservableList is used to store the values for the valuesComboBox in the MainController
     */
    protected static final ObservableList<ValuesModel> VALUES_MODEL_OBSERVABLE_LIST = FXCollections.observableArrayList(
            new ValuesModel("Relative Wert [kWh]", 1),
            new ValuesModel("Relative Wert [kW]", 2),
            new ValuesModel("Zählerstände", 3)
    );

    protected static final SDATManager SDAT_MANAGER = new SDATManager();
    protected static final ESLManager ESL_MANAGER = new ESLManager();

    public static File eslFolder = null;
    public static File sdatFolder = null;

    public abstract void init();
}