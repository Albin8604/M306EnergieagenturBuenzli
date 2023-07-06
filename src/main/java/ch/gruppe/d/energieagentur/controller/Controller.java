package ch.gruppe.d.energieagentur.controller;


import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import ch.gruppe.d.energieagentur.util.ESLManager;
import ch.gruppe.d.energieagentur.util.SDATManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Controller {
    //Values of the Values Combo Box
    protected static final ObservableList<ValuesModel> VALUES_MODEL_OBSERVABLE_LIST = FXCollections.observableArrayList(
            new ValuesModel("Relative Wert [kWh]", 1),
            new ValuesModel("Relative Wert [kW]", 2),
            new ValuesModel("Zählerstände", 3)
    );

    protected static final SDATManager SDAT_MANAGER = new SDATManager();
    protected static final ESLManager ESL_MANAGER = new ESLManager();

    public abstract void init();
}