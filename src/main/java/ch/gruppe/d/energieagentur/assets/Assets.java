package ch.gruppe.d.energieagentur.assets;

import java.net.URL;

/**
 * This enum is used to manage all the assets
 */
public enum Assets {

    ESLFiles("ESL-Files"),
    StandartStyle("css/standartStyle.css"),
    Test("test.fxml"),
    Main("main.fxml"),
    MANIFEST("META-INF/MANIFEST.MF"),
    Loading("loading.gif"),
    SDATFiles("SDAT-Files"),
    ;
    final String filename;

    /**
     * @param filename the filename of the asset
     */
    Assets(String filename) {
        this.filename = filename;
    }

    /**
     * @return the filename of the asset
     */
    public URL asUrl() {
        return Assets.class.getClassLoader().getResource(filename);
    }
}