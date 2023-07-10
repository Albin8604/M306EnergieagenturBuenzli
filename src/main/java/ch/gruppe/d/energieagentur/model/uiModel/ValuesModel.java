package ch.gruppe.d.energieagentur.model.uiModel;

/**
 * This class is used as a Model Class for the valuesComboBox in the MainController
 */
public class ValuesModel {
    private String name;
    private int value;

    /**
     * All args constructor
     *
     * @param name  name of model
     * @param value value of model
     */
    public ValuesModel(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * gets the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the value of name
     *
     * @return ValuesModel
     */
    public ValuesModel setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * gets the value of value
     */
    public int getValue() {
        return value;
    }

    /**
     * sets the value of value
     *
     * @return ValuesModel
     */
    public ValuesModel setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
