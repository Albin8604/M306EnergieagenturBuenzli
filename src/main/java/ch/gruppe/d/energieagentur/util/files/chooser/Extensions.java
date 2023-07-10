package ch.gruppe.d.energieagentur.util.files.chooser;

import javafx.stage.FileChooser;

/**
 * This Enum provides the most frequently used extensions
 */
public enum Extensions {
    CSV,
    JSON,
    PNG,
    JPG,
    BMP,
    JPEG,
    XML;

    /**
     * Returns the chosen extension as an ExtensionFilter
     * @return chosen extension as an ExtensionFilter
     */
    public FileChooser.ExtensionFilter getExtensionFilter() {
        return new FileChooser.ExtensionFilter(this.name() + " File", "*." + this.name().toLowerCase());
    }
}
