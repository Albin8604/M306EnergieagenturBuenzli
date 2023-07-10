module ICTSkills {
    requires javafx.graphics;
    requires jakarta.persistence;
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires com.fasterxml.jackson.databind;
    requires com.opencsv;
    requires java.xml.bind;
    requires jfxutils;
    requires jfreechart;
    requires java.desktop;

    opens ch.gruppe.d.energieagentur.controller to javafx.fxml;
    opens ch.gruppe.d.energieagentur.util.files.xml.model.esl to java.xml.bind;

    exports ch.gruppe.d.energieagentur.controller to javafx.fxml;

    exports ch.gruppe.d.energieagentur.model.uiModel;
    exports ch.gruppe.d.energieagentur.util.files;
    exports ch.gruppe.d.energieagentur.util.files.xml.model.esl;
    exports ch.gruppe.d.energieagentur.util.files.xml.model.adapter;
    exports ch.gruppe.d.energieagentur.util;
    exports ch.gruppe.d.energieagentur.util.files.sdat;
    exports ch.gruppe.d.energieagentur.util.files.esl;
    exports ch.gruppe.d.energieagentur.util.files.export;
    exports ch.gruppe.d.energieagentur.util.files.export.model;
    exports ch.gruppe.d.energieagentur;
    exports ch.gruppe.d.energieagentur.component to javafx.fxml;
    opens ch.gruppe.d.energieagentur.component to javafx.fxml;
}