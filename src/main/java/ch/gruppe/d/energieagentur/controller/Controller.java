package ch.gruppe.d.energieagentur.controller;


import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.files.ESLManager;
import ch.gruppe.d.energieagentur.util.files.SDATManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public abstract class Controller {
    //Values of the Values Combo Box
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