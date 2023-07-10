package ch.gruppe.d.energieagentur.model.uiModel;

/**
 * This class is used as a Model Class for the valuesComboBox in the MainController
 */
public class ValuesModel {
    private String name;
    private int value;
    
    public ValuesModel(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ValuesModel setName(String name) {
        this.name = name;
        return this;
    }

    public int getValue() {
        return value;
    }

    public ValuesModel setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return name;
    }
}
