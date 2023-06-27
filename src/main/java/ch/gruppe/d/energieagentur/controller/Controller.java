package ch.gruppe.d.energieagentur.controller;


import ch.gruppe.d.energieagentur.model.uiModel.ValuesModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Controller {
    //Values of the Values Combo Box
    protected static final ObservableList<ValuesModel> VALUES_MODEL_OBSERVABLE_LIST = FXCollections.observableArrayList(
            new ValuesModel("Relative Wert [kWh]", 1),
            new ValuesModel("Relative Wert [kW]", 2),
            new ValuesModel("Zählerstände", 3)
    );

    public abstract void init();
}