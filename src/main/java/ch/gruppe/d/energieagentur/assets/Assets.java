package ch.gruppe.d.energieagentur.assets;

import java.net.URL;

public enum Assets {

    Main("Main.fxml"),
    ;
    final String filename;

    Assets(String filename) {
        this.filename = filename;
    }

    public URL asUrl() {
        return Assets.class.getClassLoader().getResource(filename);
    }
}