package ch.gruppe.d.energieagentur.assets;

import java.net.URL;

public enum Assets {

    ESL_Files("ESL-Files"),
    StandartStyle("css/standartStyle.css"),
    Test("test.fxml"),
    Main("main.fxml"),
    MANIFEST("META-INF/MANIFEST.MF"),
    SDAT_Files("SDAT-Files"),
;
    final String filename;

    Assets(String filename){
        this.filename = filename;
    }

    public URL asUrl(){
        return Assets.class.getClassLoader().getResource(filename);
    }
}