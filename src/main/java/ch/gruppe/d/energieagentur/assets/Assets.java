package ch.gruppe.d.energieagentur.assets;

import java.net.URL;

/**
 * This ENUM provides the path to each file in the resources folder
 */
public enum Assets {

    ESLFiles("ESL-Files"),
    StandartStyle("css/standartStyle.css"),
    Test("test.fxml"),
    Main("main.fxml"),
    MANIFEST("META-INF/MANIFEST.MF"),
    Loading("loading.gif"),
    SDATFiles("SDAT-Files");
    final String filename;

    Assets(String filename) {
        this.filename = filename;
    }

    /**
     * Converts the paths to URL
     *
     * @return url of file
     */
    public URL asUrl() {
        return Assets.class.getClassLoader().getResource(filename);
    }
}